package com.mycompany.provider.aws;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.google.gson.Gson;
import com.mycompany.domain.Person;
import com.mycompany.exception.InvalidException;
import com.mycompany.provider.PersonDataProvider;


/**
 * If you are interested in testing with AWS S3, we can use this provider
 * 
 * @author chkrishna
 *
 */
@Repository
@Profile("aws")
public class AWSS3ProviderImpl implements PersonDataProvider {

	private static final Logger logger = LoggerFactory.getLogger(AWSS3ProviderImpl.class);
	
	private static AmazonS3 s3Client = null;
	
	@Override
	public void loadPersons() {
		
		AmazonS3 amazonS3 = this.createClient();
		boolean available = checkForObjectExist();
		if(available) {
			logger.info("Object already available");
			return;
		}
		this.saveObject(amazonS3);
	}

	@Override
	public String updatePersons(List<Person> personList) {
		
		Gson gson = new Gson();
		String jsonStr = gson.toJson(personList);
		logger.info("json String: "+jsonStr);
		InputStream stream = new ByteArrayInputStream(jsonStr.getBytes());
		this.saveObject(stream);
		return jsonStr;
		
	}

	@Override
	public Map<Long, Person> getPersonData() {
		
		StringWriter writer = new StringWriter();
		try {
			AmazonS3 amazonS3 = this.createClient();
			S3Object s3Object = amazonS3.getObject(new GetObjectRequest(this.getBucketName(), KEY_NAME));
			IOUtils.copy(s3Object.getObjectContent(), writer,Charset.defaultCharset());
			logger.info("Person data: "+writer); 
			
		} catch (AmazonS3Exception e) {
			e.printStackTrace();
			
			throw new InvalidException(e.getStatusCode(),e.getErrorCode(),e.getErrorMessage());
			
		} catch(IOException e) {
			logger.error("IO Exception Occurred");
		}
		return getPersonMap(writer.toString());
	}	


	
	private void saveObject(AmazonS3 amazonS3) {
		try {
			logger.info("Creating object person.json file");
			InputStream stream = this.getClass().getResourceAsStream("/json/person.json");
			ObjectMetadata metaData = new ObjectMetadata();
			metaData.setContentLength(stream.available());
			metaData.setContentType(MediaType.APPLICATION_JSON_VALUE);
			amazonS3.putObject(new PutObjectRequest(this.getBucketName(), KEY_NAME, stream, metaData));
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String getBucketName() {
		
		String bucketName = System.getProperty("aws.bucket");
		if(bucketName == null) {
			return DEFAULT_TEST_BUCKET;
		}
		logger.info("AWS Bucket Name loaded from system properties: "+bucketName);
		return bucketName;
		
	}
	
	private void saveObject(InputStream stream) {
		AmazonS3 amazonS3 = this.createClient();
		try {
			logger.info("Updating object person.json file");
			ObjectMetadata metaData = new ObjectMetadata();
			metaData.setContentLength(stream.available());
			metaData.setContentType(MediaType.APPLICATION_JSON_VALUE);
			amazonS3.putObject(new PutObjectRequest(this.getBucketName(), KEY_NAME, stream, metaData));
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private boolean checkForObjectExist() {
		logger.info("Check if object already exists");
		AmazonS3 amazonS3 = this.createClient();
		boolean objectAvailability = amazonS3.doesObjectExist(this.getBucketName(), KEY_NAME);
		
		return objectAvailability;
	}


	private AmazonS3 createClient() {
		
		if(s3Client == null) {
			logger.info("S3 Client creating for the first time...");
			s3Client = AmazonS3ClientBuilder.standard()
					.withCredentials(new DefaultAWSCredentialsProviderChain()).build();
		}
		
		return s3Client;
	}

}
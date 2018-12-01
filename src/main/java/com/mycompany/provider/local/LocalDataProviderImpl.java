package com.mycompany.provider.local;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.mycompany.domain.Person;
import com.mycompany.exception.InvalidException;
import com.mycompany.provider.PersonDataProvider;

@Repository
@Profile("local")
public class LocalDataProviderImpl implements PersonDataProvider {

	private static final Logger logger = LoggerFactory.getLogger(LocalDataProviderImpl.class);

	private static Map<Long, Person> localPersonMap = new HashMap<>();
	
	public LocalDataProviderImpl() {
		
		this.loadPersons();
	}
	
	
	@Override
	public void loadPersons() {
		
		StringWriter writer = new StringWriter();
		
		Resource resource = new ClassPathResource(KEY_NAME);
		try {
			IOUtils.copy(resource.getInputStream(), writer,Charset.defaultCharset());
			logger.info("Person data: "+writer);
		} catch (IOException e) {
			e.printStackTrace();
			throw new InvalidException(HttpStatus.SC_NOT_FOUND,"404", "Data Not Available");
		}
		localPersonMap = getPersonMap(writer.toString());
		logger.info("Local Person Map loaded successfuly with :"+localPersonMap);
		
	}

	@Override
	public Map<Long, Person> getPersonData() {
		
		return localPersonMap;
	}

	@Override
	public String updatePersons(List<Person> personList) {
		
		localPersonMap = convertPersonToMap(personList);
		
		Gson gson = new Gson();
		String jsonStr = gson.toJson(localPersonMap);
		return jsonStr;
	}

}

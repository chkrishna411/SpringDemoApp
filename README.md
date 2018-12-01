<h2> Spring Boot Demo App </h2>

A Simple Spring Boot App to run on Docker Containers.

Spring Boot App for performing CRUD operations. A sample person data is available in the following form.


/person/{id}		for GET by id.  <br/>
/persons			for GET All persons  <br/>
/person			for PUT operation, need to send Person object as RequestBody  <br/>
/person/{id}		for DELETE by id  <br/>
/persons			for POST operation, to load sample persons.  <br/>

<br/>
<b> With Local Data: </b>

By default sprint boot starts with local profile. No need to setup the data. 
Sample DataSet will be loaded during startup. 

<b> With AWS Connectivity: </b>

System Properties Needed if interested in connecting to aws: <br/> 

 -Daws.accessKeyId=$accessKey  <br/>
 -Daws.secretKey=$secretKey  <br/>
 -Daws.bucket=krish-json-bucket <br/>
 -Dspring.profiles.active=aws <br/>
 
 If aws.bucket system property was not available, the bucket name was defaulted to "krish-json-bucket".
 As of now bucket creation was not available,
 
 So Make sure that Bucket already exists in AWS, and also it has Read/Write permission for the user.
 Please provide valid S3 Access Credentials using system parameters.
 
 instead of system properties, you can also pass aws credentials as ENVIRONMENT variables. use the following names
 
 export AWS_ACCESS_KEY_ID=$accessKey    <br/>
 export AWS_SECRET_ACCESS_KEY=$secretKey  <br/>
 export AWS_REGION=$region  <br/>
 
 Once after checking out the code, run the docker build command inside the project, make sure your docker is running.
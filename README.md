A Simple Spring Boot App to run on Docker Containers.

Spring Boot App for performing CRUD operations. A sample person data is avaialble in the following form.

/person/{id}		for GET by id.
/persons			for GET All persons
/person				for PUT operation, need to send Person object as RequestBody
/person/{id}		for DELETE by id
/persons			for POST operation, to load sample persons.

System Properties Required:
------------ ------------- 
 -Daws.accessKeyId=<accessKey> 
 -Daws.secretKey=<secretKey> 
 -Daws.bucket=krish-json-bucket
 
 If aws.bucket system property was not available, the bucket name was defaulted to "krish-json-bucket".
 As of now bucket creation was not available,
 
 So Make sure that Bucket already exists in AWS, and also it has Read/Write permission for the user.
 Please provide valid S3 Access Credentials using system parameters.
 
 Once after checking out the code, run the docker build command inside the project, make sure your docker is running.
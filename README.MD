# JAVA SAMPLE PROJECT

### mvn

Using maven to build and run the application

`mvn clean build`

`cp target/javasampleproject-1.0-SNAPSHOT.jar .`

`java -jar javasampleproject-1.0-SNAPSHOT.jar`

### docker
The following guides illustrate how to use some features concretely:

`mvn spring-boot:build-image -Dspring-boot.build-image.imageName=correvate`

`docker run -p 8080:8080 -t  correvate -name correvate-file-upload`


## clean up

`docker rm correvate-file-upload` # to remove container

`docker rmi correvate` # to remove image

`mvn clean` # to remove target folder

## Use below collection to test the api

[postman collection](correvate.postman_collection.json)
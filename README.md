# ISAID-API

>> Development in progress

### Description

Main REST API of the ISAID Project.
Currently it provides the following resources:

  * Prophet (/prophet);

### Development

#### Install requirements

- Java >= 11

#### Run

This will run the application in development mode:

```bash

git clone https://github.com/leohoc/isaid-api
cd isaid-api/
./gradlew clean build test
java -jar build/libs/*.jar

```

This will build and run the application in your local machine. Wait for the starting process, 
then navigate to `http://localhost:8080/swagger-ui.html` to access the API documentation

Hit Ctrl+C in the terminal to shutdown.
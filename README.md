# ISAID-API

> Development in progress

### Description

Main REST API of the ISAID Project.
Currently it provides the following resources:

  * Prophet (/prophets);
  * Prophecy (/prophets/{login}/prophecies)

### Development

#### Install requirements

- Java >= 11
- Docker >= 19

#### Create a local DynamoDB instance

The isaid-api uses DynamoDB to store its data.
For development purposes, you can run a local instance in your machine, following the instructions of the [isaid-database-migration](https://github.com/leohoc/isaid-database-migration) project.

#### Run

Running the application in development mode:

```bash

git clone https://github.com/leohoc/isaid-api
cd isaid-api/
./gradlew clean build test acceptanceTest
java -jar build/libs/*.jar

```

This will build and run the application in your local machine. Wait for the starting process, 
then navigate to `http://localhost:8080/swagger-ui.html` to access the API documentation

Hit Ctrl+C in the terminal to shutdown.

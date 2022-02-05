[![Java CI with Maven](https://github.com/Brest-Java-Course-2021-2/Aliaksandr-Remizau-Municipal-service/actions/workflows/maven.yml/badge.svg)](https://github.com/Brest-Java-Course-2021-2/Aliaksandr-Remizau-Municipal-service/actions/workflows/maven.yml)
[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
### Brest Java Course 2021 2

# Municipal service

This is simple 'Municipal Service' Spring Boot web application for record-keeping tenant's requests of repairs.

* [Documentation](documentation/MunicipalService-specification(ENG).md)
* [Available Endpoints](documentation/EndpointsMunicipalService.md)
## Requirements

* JDK 11+

* Git 2.25.1+

* Apache Maven 3.6.3+

## Installing

    git clone https://github.com/Brest-Java-Course-2021-2/Aliaksandr-Remizau-Municipal-service.git
    cd Aliaksandr-Remizau-Municipal-service


## Build project

    mvn clean install

* To start Rest server (rest-app module):

      java -jar ./rest-app/target/rest-app-1.0-SNAPSHOT.jar

The rest application will be available at [http://localhost:8088](http://localhost:8088).


* To start WEB application (web-app module):

      java -jar ./web-app/target/web-app-1.0-SNAPSHOT.jar

The web application will be available at [http://localhost:8080](http://localhost:8080).

## REST API Documentation

API documentation with Swagger UI:
[http://localhost:8088/swagger-ui.html](http://localhost:8088/swagger-ui.html)

The OpenAPI 3.0 descriptions in JSON format will be available at the path:
[http://localhost:8088/v3/api-docs](http://localhost:8088/v3/api-docs)

##Postman
To test the REST API, you can use the Postman collection to access the API endpoints:
[Postman endpoints json](documentation/MS.postman_collection.json)
 

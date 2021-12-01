[![Java CI with Maven](https://github.com/Brest-Java-Course-2021-2/Aliaksandr-Remizau-Municipal-service/actions/workflows/maven.yml/badge.svg)](https://github.com/Brest-Java-Course-2021-2/Aliaksandr-Remizau-Municipal-service/actions/workflows/maven.yml)
### Brest Java Course 2021 2

# Municipal service

This web application for record-keeping  tenants' requests for repairs .

## Requirements

* JDK 11
* Apache Maven

## Build application:
```
mvn clean install
```
## Run integration tests:
```
mvn clean verify
```

## Run project information ( coverage, dependency, etc. ):
```
mvn site
mvn site:stage
open in browser: ${project}/target/staging/index.html

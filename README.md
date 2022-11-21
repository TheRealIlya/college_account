# College Account Service

## The purpose of the application

- Application has been created as a final project for Java Development course at IT Academy. The main goals of application
were to learn new technologies and get some experience in writing Java apps. Application was reworked lately to cover some more
technologies

## Functionality description

- various CRUD-operations for interconnected models (Person, Theme, Group, Grade)

## Application structure

- Full application consists of 3 microservices:
1. **College Auth** (this application) - gateway, which provides authentication, authorization and entry points to logic of the whole system
2. **College Core** - application which provides logic for Theme, Group and Grade entities
3. **College Logger** - application which reformats and stores logs

- Application has **Jenkinsfile** which is set up to run tests, install the app and provide SonarQube check

## Implementation information

1. **Programming language: Java**
2. **Java version: 11**

Used technologies

- **Spring Boot** - used in *all apps*
- **Spring Data** - used in *Auth and Core* microservices
- **Spring Security** - used in *Auth* microservice
- **JWT** - used in *Auth* microservice
- **Kafka** - used in *Auth and Logger* microservices for logs transferring
- **Elastic stack (Elasticsearch, Logstash, Kibana)** - used in *Logger* microservice for storing, reformatting and browsing logs
- **Swagger** - used in *Auth and Core* microservices to provide endpoints documentation
- **Liquibase** - used in *Auth* microservice for PostgreSQL migration
- **Mongock** - used in *Core* microservice for MongoDB migration
- **Mapstruct** - used in *Auth and Core* apps for transferring entities to DTOs
- **Lombok** - used in *all apps*
- **JUnit 5** - used in *Auth* microservice
- **Mockito** - used in *Auth* microservice

Database management systems

- **PostgreSQL** - used for managing Users in *Auth* microservice
- **MongoDB** - used for managing Themes, Grades and Groups in *Core* microservice

Containerization system

- Docker
- Docker Compose

## Launching the app

You can run application in Docker using steps below:
- Clone branch 'dev' from project git repository
- Open Terminal in cloned project directory and run command "docker-compose up -d"
- After application has started, Swagger UI will be available through link http://localhost:8080/swagger-ui/index.html
- Kibana for searching logs is available through link http://localhost:5601

**Default users for authorization are described in package web/src/main/resources/users_passwords**

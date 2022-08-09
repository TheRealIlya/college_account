# College Account Service

## The purpose of the application

- Application has been created as a final project for Java Development course at IT Academy. The main goals of application
were to learn new technologies and get some experience in writing Java apps.

## Functionality description

- various CRUD-operations for interconnected models (Person, Theme, Group, Grade)

## Implementation information

1. **Programming language: Java**
2. **Java version: 11**

Used technologies

- Spring Boot
- Spring Data
- Spring Security
- JWT
- Swagger
- Liquibase
- Mapstruct
- Lombok
- JUnit 5
- Mockito

Database management system

- PostgreSQL

Containerization system

- Docker
- Docker Compose

## Launching the app

You can run application in Docker using steps below:
- Clone branch 'dev' from project git repository
- Open Terminal in cloned project directory and run command "docker-compose up -d"
- After application has started, Swagger UI will be available through link http://localhost:8080/swagger-ui/index.html

**Default users for authorization are described in package web/src/main/resources/users_passwords**

# Spring Boot Project

## Introduction
This is a Spring Boot project that provides a RESTful API for managing resources. It is built using Spring Boot, Spring Data JPA, and MySQL.

## Features
- REST API for CRUD operations
- Spring Boot framework
- MySQL database integration
- Swagger documentation

## Prerequisites
Ensure you have the following installed:
- Java 21
- MySQL database

## Installation
1. Clone the repository:
   ```sh
   git clone git clone https://github.com/hiepnx03/2024-04-Fullstack-Springboot-React-Mysql-CameraShop.git
   cd 2024-04-Fullstack-Springboot-React-Mysql-CameraShop/demo
   ```

2. Configure the database settings in `application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/your_database
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

3. Build and run the application:
   ```sh
   mvn clean install
   mvn spring-boot:run
   ```

## API Documentation
After running the application, the API documentation can be accessed at:
```
http://localhost:8080/swagger-ui.html
```

## Running Tests
To run tests, use:
```sh
mvn test
```

## License
This project is licensed under the MIT License.

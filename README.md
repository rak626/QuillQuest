# QuillQuest

This is a Java application for a blog website, where you can 
create, upload and delete different type of Post as well as 
you can add, update, remove comments to each post.
Application can filter post, comments based on different parameter as well.
Application used Role Based Authentication where Admin has all rights whereas
user has few restricted operations.

------------



### Requirements
- Java 8 or higher
- Maven
- MySQL Server
- Postman or similar as a client

------------


### Technologies used
- Spring Boot
- Spring MVC
- Spring Data JPA
- Spring Security
- JWT Authentication
- Hibernate
- MySQL
- JUnit5
- Mockito

------------


### Installation and Usage


1. Clone the repository to your local machine:
   `git clone https://github.com/your-username/blog-application.git`
2. Open the application.properties file located in src/main/resources and modify the following properties according to your MySQL configuration:

```
spring.datasource.url=jdbc:mysql://localhost:3306/db_name
spring.datasource.username=root
spring.datasource.password=root_password
```
> Run the following command to build the project:

`mvn clean install`

> Run the application using the following command:


`java -jar target/blog-application-0.0.1-SNAPSHOT.jar`


------------


### Features
- Used **JWT Token** authentication to make Serverless authentication
- Used **Role Based** authentication(User, Admin)
- CRUD operations using Rest APIs
- Handling **Images** while taking post entity
- Used **Pagination** for optimized search in the database
- Custom Error and Exception Handling
- Unit testing using JUnit5 and Mockito

------------


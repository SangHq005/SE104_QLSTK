# Welcome to MonNes 👋

This is a Spring Boot backend project for managing savings accounts.

## Get started

1. Install dependencies

   ```bash
   mvn clean install
   ```

2. Set up MySQL

   ```bash
    CREATE DATABASE qlstk_db;
   ```
3. Update src/main/resources/application.properties with your MySQL credentials:

  ```bash
   spring.datasource.url=jdbc:mysql://localhost:3306/qlstk_db?createDatabaseIfNotExist=true
   spring.datasource.username=root
   spring.datasource.password=<your-mysql-password>
   spring.jpa.hibernate.ddl-auto=update
  ```
4. Start the app

   ```bash
   mvn spring-boot:run
   ```

#In the output, you'll find the server running at:

Local server: http://localhost:8080
Swagger UI: http://localhost:8080/swagger-ui.html (to explore and test APIs)
API docs: http://localhost:8080/api-docs (OpenAPI JSON)

# Toll Parking REST

This is a REST API for handling Toll Parking request. It has been implemented using Spring Boot and Spring Data JPA for 
handling the database interactions.

## Usage
If you are using IntelliJ, you will have to install the lombok plugin.

For launching the application, you can right-click the public static void main in TollParkingRestApplication and select 
Run from your IDE, or use 
```bash
mvn clean spring-boot:run
```

## Testing
You can find in the root directory a Postman collection with all the necessary requests.

## Update 9 May:
You can now access the Swagger on http://localhost:8080/swagger-ui.html.

## Also see
https://github.com/adriano-grimolizzi/parking-app for a non REST API.

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

## Also see
https://github.com/adriano-grimolizzi/parking-app for a non REST API.

## Update 9 May:
You can now access the Swagger on http://localhost:8080/swagger-ui.html.

## Update 19 May:
You can use the branch "mysql" to test the application using a 'real' MySQL instance (instead of an in-memory H2 database) running on Docker. 
To run and configure the docker container for MySQL you can use the following:
```bash
docker run --name=mysql-container -p 3306:3306 -d mysql/mysql-server
```
You can obtain the GENERATED ROOT PASSWORD by checking the logs on the container:
```bash
docker logs mysql-container
```
Use it in the next step:
```bash
docker exec -it mysql-container mysql -uroot -p

mysql> ALTER USER 'root'@'localhost' IDENTIFIED BY 'password';

mysql> create database db_example;

mysql> create user 'user'@'%' identified by 'pass';

mysql> grant all on db_example.* to 'user'@'%'; 
```
then start the application on localhost like usual (see the 'Usage' section above).


NB: I'm using Docker Tollbox, as a normal docker installation is not possible on Windows 10 Home edition. That's why in the application.properties file the MySQL url is on 192.168.99.100. If you are using Windows Pro, you should change it to localhost.

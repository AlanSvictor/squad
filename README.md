### Technologies Used
![java](https://img.shields.io/badge/Java-17-green?style=plastic&logo=java)
![Spring](https://img.shields.io/badge/Spring-2.6+-green?style=plastic&logo=spring)
![JUnit](https://img.shields.io/badge/JUnit-4-green?style=plastic&)
![Maven](https://img.shields.io/badge/Maven-gray?style=plastic)
![Lombok](https://img.shields.io/badge/Lombok-gray?style=plastic)
![Feign](https://img.shields.io/badge/Cloud-Feign-gray?style=plastic)


### Guidelines:

	* Java version: 17

	* Maven version: 3.8.4

	* port: 8082

	* The application does not need a real database, for this the H2 memory database is used;

	* Database console: http://localhost:8082/h2-console/login.jsp

	* User database: sa

	* password: password

# Approach

 A abordagem utilizada para desenvolver o teste foi de microsserviços com separação de responsabilidades. 
 Para isso, foram criados dois controllers, RoleController e MemberRoleController.

### To run the application
 ```bash
mvn spring-boot:run
```

### Improvements for User and Team microservices!
 * Create queries with pagination
 * Create queries with filters and if possible Elasticsearch
 * To use JWT Oauth2
 * Create a field in User and Team database with status
 * Create logs
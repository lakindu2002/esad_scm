# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.7.4/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.7.4/maven-plugin/reference/html/#build-image)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.7.4/reference/htmlsingle/#data.sql.jpa-and-spring-data)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.7.4/reference/htmlsingle/#web)

### Guides
The following guides illustrate how to use some features concretely:

* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)

### Structure
API - Defined in the `controller` package. There is already a sample `ping` endpoint declared to demonstrate HTTP REST API in Spring Boot.

### Resource Declaration
In `src/main/resources/` there is a file titled `application.properties`. The database information will be configured there to config the JDBC driver.

### DB

Update DB Username and PW and URL by navigating to `application.properties` and giving the required information.

### Information on Lakindu's Work


GOF Patterns Used
- Facade: API Service Communication: Structural
- Singleton: JWT Token Manager: Creational
- Template Method: Item Creation: Behavioural
- Strategy: Raw Material Sorting :Behavioural

Presentation Layer Patterns Used
- Service To Worker Pattern - Forwarding requests from controller to business layer.

Business Layer Patterns Used
- Transfer object - Used to compile DTOs.
- Business Delegate Pattern

Data Layer Patterns Used
- Repository

EAI
- Use of REST Services in the SOA to link Frontend and Backend.
- Use of Token Based Security To Enhance Security on All Backend Resources


How Extendable - everything is coded to an interface. Very easy to swap out implementations in the future.

How secure - Use of Spring Security and Token Based Authorisation.

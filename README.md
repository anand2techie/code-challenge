# Getting Started
Currency exchange service as part of coding challenge, developed in Java 8 and latest version of Spring Boot (2.2.1.RELEASE) 

# Command to run the application
- mvn spring-boot:run
- docker run -p 8080:8080 -t springio/gs-spring-boot-docker (to run using docker)

# Project Design
- Adopted clean & test-driven design for developing REST APIs.
- Adhered to REST API design standards, ensuring right HTTP methods are used with proper response codes.
- Project structure is laid out with separation of concerns in the first place. Used Hyperlinks/HATEOAS for discoverability.
- Used H2 as the database; Spring data JPA as repository support
- Lombok is used for concise-code writing

# Approach
There is a scheduler configured on a time everyday which syncs data from external currency conversion provider (suggested approach by the currency conversion provider) and caches into
local H2 database and use that for conversion for the entire day, for faster and efficient conversion. Whenever the application
gets restarted, data will be sync'ed again with external system. Sync frequency is configurable externally, thus providing
flexibility.

# Highlights
- Handwritten request and response models for APIs (refer apimodel package)
- Dedicated controller for Exception handling (ErrorController)
- Reusable components (e.g. CreateAndUpdateEntityBase & EntityBase)
- Swagger Configuration (refer http://localhost:8080/swagger-ui.html)
- Extensive test coverage (API integration testing & Unit testing)
- Slf4j for logging

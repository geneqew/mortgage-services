## ING - Mortgage Services

---
### Description:

This is a RESTful web services for mortgage application and rates checking

### Requirements
- JDK 21; This is tested against amazoncorretto
- Maven
- Docker

### Setup

- Build and run this project locally as a jar via:
`mvn clean install spring-boot:run`
- Or via docker by building it with: `docker build -t ing-mortgage-services .` and run as: `docker run -p 8080:8080 ing-mortgage-services`

### API Endpoints
- App comes with generated docs and you can also test the available endpoints through `http://localhost:8080/swagger-ui/index.html` after starting up the project 
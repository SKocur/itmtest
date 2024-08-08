# ITMagination recruitment test project

JDK 21 is required to compile and run this project.
Docker needs to be installed to run integration and performance tests too.

## Local development

### Run unit & integration tests locally
To run unit tests:
```bash
./mvnw test
```

To run integration tests:
```bash
./mvnw verify
```

### Run application locally
Firstly you need to run required services like Postgres database and RabbitMQ.
To do so run:
```bash
docker compose up
```

Build app:
```bash
./mvnw package
```
and then run jar:
```bash
java -jar ./target/itmtest-1.0.0-SNAPSHOT.jar
```

#### Access OpenAPI definition
Open http://localhost:8080/v3/api-docs

#### After application is run, you can execute performance tests
```bash
./mvnw gatling:test
```

FROM alpine/java:21-jdk AS build
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

RUN ./mvnw dependency:go-offline
COPY src ./src
RUN ./mvnw clean package -DskipTests

FROM alpine/java:21-jdk
RUN adduser -D -H -s /bin/sh appuser
WORKDIR /app

COPY --from=build /app/target/itmtest-1.0.0-SNAPSHOT.jar ./app.jar
RUN chown -R appuser:appuser /app && chmod -R 755 /app
USER appuser

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]

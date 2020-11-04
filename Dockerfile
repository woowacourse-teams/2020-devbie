FROM openjdk:8-jdk-alpine

COPY devbie-app-api/build/libs/devbie-app-api-1.0.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

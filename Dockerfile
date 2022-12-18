FROM openjdk:18-ea-11-jdk-alpine3.15

ARG JAR_FILE=/build/libs/schoolregistration-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/NewspaperApi-0.1.0-SNAPSHOT.jar
COPY ${JAR_FILE} application.jar
ENTRYPOINT ["java","-jar","/application.jar"]
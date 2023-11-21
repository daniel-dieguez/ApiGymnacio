FROM openjdk:8-jdk-alpine
MAINTAINER Daniel Dieguez
COPY target/gymAntigua-0.0.1-SNAPSHOT.jar gymAntigua-1.0.0.jar
ENTRYPOINT ["java","-jar","/gymAntigua-1.0.0.jar"]
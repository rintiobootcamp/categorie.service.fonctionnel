FROM openjdk:8u111-jdk-alpine
VOLUME /tmp
ADD /target/categorieService.jar app.jar
EXPOSE 6082
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]


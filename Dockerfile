FROM openjdk:8-jdk-alpine
ADD target/categorieService.jar categorieService_sf.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","categorieService_sf.jar"]
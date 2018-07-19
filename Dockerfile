FROM openjdk:8-jdk-alpine
ADD target/categorieService.jar categorieService_sf.jar
EXPOSE 6082
ENTRYPOINT ["java","-jar","categorieService_sf.jar"]
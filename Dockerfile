FROM ibrahim/alpine
ADD target/categorieService.jar ws_categorieService_sf.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","categorieService.jar"]
FROM openjdk:8-jdk-alpine
ADD target/categorieService.jar categorieService_sf.jar
EXPOSE 6082
ENV JAVA_OPTS="-XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap  -XX:MaxRAMFraction=1 -XshowSettings:vm "
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar categorieService_sf.jar" ]
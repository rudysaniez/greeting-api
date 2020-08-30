FROM openjdk:12.0.2

EXPOSE 8080

ADD ./target/greeting-api-0.0.1-SNAPSHOT.jar greeting-api.jar

ENTRYPOINT [ "java", "-jar", "/greeting-api.jar" ]
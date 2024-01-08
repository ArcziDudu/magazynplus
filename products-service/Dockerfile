FROM openjdk:17

COPY target/*.jar app.jar

ENTRYPOINT ["java", "-Xmx300m", "-jar", "/app.jar"]
FROM openjdk:11-jdk-slim
COPY /build/libs/isaid-api*.jar isaid-api.jar
ENTRYPOINT ["java", "-jar", "/isaid-api.jar"]
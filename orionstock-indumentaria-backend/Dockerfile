FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/orionstock-indumentaria-backend-0.0.1.jar
COPY ${JAR_FILE} app_orionstock.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app_orionstock.jar"]
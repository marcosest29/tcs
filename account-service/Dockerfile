FROM eclipse-temurin:21-jdk as builder

COPY build/libs/account-service-0.0.1-SNAPSHOT.jar /app/app.jar

# Sets the working directory inside the container
WORKDIR /app

# Command to run Spring Boot application when container starts
CMD ["java", "-jar", "app.jar"]
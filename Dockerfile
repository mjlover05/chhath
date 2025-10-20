# # Use official OpenJDK 17 image as base
# FROM openjdk:17-jdk-slim

# # Set environment variable for Java options (optional)
# ENV JAVA_OPTS=""

# # Set working directory inside container
# WORKDIR /app

# # Copy the jar file into the container
# COPY target/chhath-0.0.1-SNAPSHOT.jar app.jar

# # Expose the port your Spring Boot app runs on
# EXPOSE 8080

# # Command to run the jar
# ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]

# Stage 1: build the app
FROM maven:3.9.1-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: run the app
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/chhath-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]


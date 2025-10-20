# Use official OpenJDK 17 image as base
FROM openjdk:17-jdk-slim

# Set environment variable for Java options (optional)
ENV JAVA_OPTS=""

# Set working directory inside container
WORKDIR /app

# Copy the jar file into the container
COPY target/chhath-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your Spring Boot app runs on
EXPOSE 8080

# Command to run the jar
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]

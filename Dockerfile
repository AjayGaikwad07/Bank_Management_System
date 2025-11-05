# ===============================
# Stage 1: Build (optional if already built)
# ===============================
# If you already have the JAR built locally, skip this stage.
#FROM maven:3.9.6-eclipse-temurin-17 AS build
#COPY . .
#RUN mvn clean package -DskipTests

# ===============================
# Stage 2: Run Spring Boot Application
# ===============================
#FROM openjdk:17
#FROM openjdk:17-jdk-slim
# Base Image (Java 17)
FROM eclipse-temurin:17-jdk
# Set working directory
WORKDIR /app

# Copy the built jar file into the container
COPY target/ATM_Machine-0.0.1-SNAPSHOT.jar app.jar

# Environment variables (can be overridden in AWS)
# --- AWS Database (Production) ---
ENV SPRING_DATASOURCE_URL=jdbc:mysql://studentdb.c3wm2akue7f8.eu-north-1.rds.amazonaws.com:3306/student_MS
ENV SPRING_DATASOURCE_USERNAME=admin
ENV SPRING_DATASOURCE_PASSWORD=root1234

# --- Optional: Local Database (for local run only) ---
# ENV SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/ATM_Machine
# ENV SPRING_DATASOURCE_USERNAME=root
# ENV SPRING_DATASOURCE_PASSWORD=root

# Expose the application port (default: 8080)
EXPOSE 1002

# Start the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

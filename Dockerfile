# Use a base image with Java 8 and Maven installed
FROM maven:3.9.3-ibmjava-8 AS build

# Set the working directory in the container
WORKDIR /app

# Copy the project files to the container
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Second stage: create a minimal Docker image with the built JAR
FROM openjdk:8-jre-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the built JAR file from the build stage to the container
COPY --from=build /app/target/OrderManagementAPI-1.0.0.jar .

# Set the command to run the application
CMD ["java", "-jar", "OrderManagementAPI-1.0.0.jar"]
# Stage 1: Build the JAR using Maven
FROM maven:3.9.4-amazoncorretto-21 AS build

WORKDIR /app

# Copy pom.xml and download dependencies (caching step)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy all source code
COPY src ./src

# Build the application JAR
RUN mvn clean package -DskipTests

# Stage 2: Run the JAR using Amazon Corretto JDK only (smaller image)
FROM amazoncorretto:21

WORKDIR /app

# Copy the built JAR from the previous stage
COPY --from=build /app/target/*.jar application.jar

# Run the JAR
ENTRYPOINT ["java", "-Xmx2048M", "-jar", "application.jar"]
FROM amazoncorretto:21

# Set working directory
WORKDIR /app

# Copy JAR directly
COPY target/*.jar application.jar

# Run the Java application
ENTRYPOINT ["java", "-Xmx2048M", "-jar", "application.jar"]

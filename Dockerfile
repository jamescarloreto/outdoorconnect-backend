# Use an official OpenJDK 21 runtime as a parent image (with JDK, not just JRE)
FROM eclipse-temurin:21-jdk-alpine

# Set a working directory in the container
WORKDIR /app

# Add metadata about the image
LABEL maintainer="jamescarl533@gmail.com"
LABEL version="0.0.1-SNAPSHOT"
LABEL description="Outdoor Connect"

# Expose the port your application will run on (default for Spring Boot is 8080)
EXPOSE 8080

# Copy your project files into the container (assuming you have the mvnw script in your project)
COPY . /app

# Ensure mvnw script has execute permissions
RUN chmod +x mvnw

# Build the JAR using Maven (inside the container)
RUN ./mvnw clean package -DskipTests

# Move the built JAR to the right location
RUN mv target/outdoorconnect-backend-0.0.1-SNAPSHOT.jar app.jar

# Set environment variables for PostgreSQL (you'll likely override these in Render's dashboard)
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/outdoorconnectdb
ENV SPRING_DATASOURCE_USERNAME=postgre
ENV SPRING_DATASOURCE_PASSWORD=1234
ENV JWT_KEY=B462CCCF881E5FB2D0508C6AE78124EF0A437E79FD1903777ECC051A989D7EDB
ENV MAIL_PASS="jvne rjav orsg oyjt"

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]

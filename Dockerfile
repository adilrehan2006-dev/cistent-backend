# Stage 1: Build the JAR
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY cistent/pom.xml .
COPY cistent/src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run the JAR
FROM openjdk:17.0.1-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080

# Move the secret file into place and start the server
ENTRYPOINT ["sh", "-c", "mkdir -p src/main/resources && cp /etc/secrets/firebase-service-account.json src/main/resources/firebase-service-account.json && java -jar app.jar"]

FROM maven:3.9.6-eclipse-temurin-17

WORKDIR /app

# Copy user-service code
COPY user-service/ .



CMD ["./mvnw", "spring-boot:run"]
EXPOSE 8081

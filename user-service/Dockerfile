FROM maven:3.9.6-eclipse-temurin-17

WORKDIR /app

# Copy user-service code
COPY user-service/ .

# Copy the shared JAR now accessible within build context
COPY security-shared/target/security-shared-0.0.1-SNAPSHOT.jar ./libs/

CMD ["./mvnw", "spring-boot:run"]
EXPOSE 8081

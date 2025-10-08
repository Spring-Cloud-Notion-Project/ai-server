# ESTÁGIO 1: Build (Construção) - "Builder"
FROM maven:3.9-eclipse-temurin-21 AS builder

WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package -DskipTests

# ESTÁGIO 2: Run (Execução) - "Runner"
FROM eclipse-temurin:21-jre

WORKDIR /app
RUN addgroup --system spring && adduser --system --ingroup spring springuser
USER springuser
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
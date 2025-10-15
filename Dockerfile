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

# instalar o Node.js e o npm para poder rodar comando npx
USER root
RUN apt-get update && \
    apt-get install -y nodejs npm && \
    npm install -g @notionhq/notion-mcp-server && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

RUN addgroup --system spring && adduser --system --ingroup spring springuser
USER springuser
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
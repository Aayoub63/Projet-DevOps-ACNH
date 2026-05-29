FROM maven:3.8.6-openjdk-8-slim AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package -DskipTests

FROM amazoncorretto:8-alpine
# Création d'un utilisateur non-root pour des raisons de sécurité
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# On copie l'artefact en donnant les droits au nouvel utilisateur
COPY --from=build --chown=spring:spring /app/target/*.jar app.jar

# Healthcheck intégré pour vérifier que l'API Spring Boot répond
HEALTHCHECK --interval=30s --timeout=10s --start-period=40s --retries=3 \
  CMD wget -q --spider http://localhost:8080/arts || exit 1

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
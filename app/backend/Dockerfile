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

# Healthcheck : endpoint actuator (port 8081, non exposé par Traefik).
# Il vérifie aussi la connexion à la base de données.
HEALTHCHECK --interval=30s --timeout=10s --start-period=40s --retries=3 \
  CMD wget -q --spider http://localhost:8081/actuator/health || exit 1

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
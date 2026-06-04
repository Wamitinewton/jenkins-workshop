# Stage 1: build the fat JAR
FROM eclipse-temurin:25-jdk-noble AS builder
WORKDIR /app

# Copy wrapper + pom first so dependency layer is cached
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline -q

COPY src ./src
RUN ./mvnw package -DskipTests -q

# Stage 2: minimal runtime image
FROM eclipse-temurin:25-jre-noble
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

# Build stage
FROM gradle:8.5-jdk21 AS build
WORKDIR /app
COPY . .
RUN gradle build --no-daemon -x test

# Run stage
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/app/build/libs/*.jar app.jar
COPY app/src/main/java/ticket/booking/localDB /app/localDB

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

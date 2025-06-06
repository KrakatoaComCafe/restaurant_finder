FROM gradle:8.14.0-jdk21 as build
COPY . /app
WORKDIR /app
RUN gradle build --no-daemon

FROM eclipse-temurin:21-jre
COPY --from=build /app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
FROM gradle:8.7-jdk21-alpine AS build
WORKDIR /app
COPY . .
RUN gradle :app:build --no-daemon -x test

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=build /app/app/build/libs/*-all.jar app.jar

RUN addgroup -S app && adduser -S app -G app
USER app

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

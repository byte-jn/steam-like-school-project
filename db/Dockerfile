FROM gradle:8.7-jdk21-alpine AS build
WORKDIR /src
COPY .. .
RUN gradle clean build -x test

FROM eclipse-temurin:21-jre-alpine
WORKDIR /src

RUN mkdir -p /data
COPY --from=build build/libs/*.jar app.jar

RUN addgroup -S app && adduser -S app -G app
USER app

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

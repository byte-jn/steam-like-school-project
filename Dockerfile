FROM gradle:8.7-jdk21-alpine AS build
WORKDIR /app
COPY . .
RUN gradle :app:build --no-daemon -x test

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=build /app/app/build/docker/main/layers/libs ./libs/
COPY --from=build /app/app/build/docker/main/layers/project_libs ./libs/
COPY --from=build /app/app/build/docker/main/layers/snapshot_libs ./libs/
COPY --from=build /app/app/build/docker/main/layers/resources ./resources/
COPY --from=build /app/app/build/docker/main/layers/app/application.jar ./application.jar

RUN addgroup -S app && adduser -S app -G app
USER app

EXPOSE 8080

ENTRYPOINT ["java", "-cp", "./libs/*:./resources:./application.jar", "org.example.Main"]

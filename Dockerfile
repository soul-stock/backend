FROM openjdk:21-jdk-slim AS build

WORKDIR /app

COPY gradlew .
COPY gradle ./gradle

EXPOSE 8080

CMD ["./gradlew", "bootRun", "--no-daemon"]
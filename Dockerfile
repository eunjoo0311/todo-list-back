# 1) build stage
FROM gradle:8.7-jdk17 AS builder
WORKDIR /app

# dependency 캐시용
COPY build.gradle settings.gradle gradlew ./
COPY gradle gradle

RUN ./gradlew dependencies --no-daemon || true

# source copy
COPY src src

RUN ./gradlew clean bootJar --no-daemon

# 2) run stage
FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
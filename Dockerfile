# ======================
# 1. Build stage
# ======================
FROM gradle:jdk21-alpine AS builder
WORKDIR /app

# Gradle Wrapper 관련 파일들을 먼저 복사하여 일관된 빌드 환경을 보장
# COPY gradlew ./
# COPY gradle ./gradle/
# COPY build.gradle settings.gradle ./
#
# RUN chmod +x ./gradlew
CMD ["./gradlew", "bootRun", "--no-daemon"]

# build.gradle 파일이 변경되지 않으면 레이어가 재사용되어 빌드 속도 향상
# RUN ./gradlew dependencies --no-daemon

# 소스 코드 복사
# COPY src ./src

# 애플리케이션을 빌드하여 JAR 파일 생성
# RUN ./gradlew bootJar --no-daemon


# ======================
# 2. Runtime stage
# ======================
# FROM eclipse-temurin:21-jdk-alpine
# WORKDIR /app

# 보안 강화를 위해 non-root 사용자를 생성하여 애플리케이션 실행
# RUN addgroup --system spring && adduser --system --ingroup spring spring
# USER spring

# 빌드된 JAR 파일의 경로를 지정
# ARG JAR_FILE=build/libs/*.jar
# COPY --from=builder /app/${JAR_FILE} app.jar

# 컨테이너가 포트를 외부에 노출하도록 설정
# EXPOSE 8080

# ENTRYPOINT ["java", "-jar", "app.jar"]
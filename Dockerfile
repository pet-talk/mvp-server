# 빌드 이미지로 사용할 베이스 이미지를 설정합니다.
FROM gradle:7.6.4-jdk17-alpine AS build

# 작업 디렉토리를 설정합니다.
WORKDIR /app

# 소스 코드를 복사합니다.
COPY . /app

# 빌드를 수행합니다.
RUN gradle clean build --no-daemon

# 런타임 이미지를 설정합니다.
FROM amazoncorretto:17-alpine3.19-jdk

# 작업 디렉토리를 설정합니다.
WORKDIR /app

# 빌드 이미지에서 빌드 결과물을 복사합니다.
COPY --from=build /app/build/libs/*.jar /app/petalk.jar

# 환경 변수를 설정합니다.
ENV	USE_PROFILE dev

# 컨테이너에서 사용할 포트를 설정합니다.
EXPOSE 8080

# 애플리케이션을 실행합니다.
CMD ["java", "-Dspring.profiles.active=${USE_PROFILE}", "-jar", "petalk.jar"]
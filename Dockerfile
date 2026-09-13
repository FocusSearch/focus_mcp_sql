FROM gradle:8.12-jdk23 AS build
WORKDIR /home/gradle/src
COPY . .
RUN gradle bootJar --no-daemon --console=plain
FROM eclipse-temurin:23-jre
WORKDIR /app
ENV SPRING_MAIN_BANNER_MODE=off SPRING_MAIN_LOG_STARTUP_INFO=false SPRING_MAIN_WEB_APPLICATION_TYPE=none LOGGING_LEVEL_ROOT=OFF
COPY --from=build /home/gradle/src/build/libs/focus_mcp_sql.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

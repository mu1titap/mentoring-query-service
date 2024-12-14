
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY build/libs/section-0.0.1-SNAPSHOT.jar mentoring-query-service.jar
COPY src/main/resources/caDeply.crt /app/caDeply.crt

EXPOSE 9300

ENTRYPOINT ["java", "-jar", "mentoring-query-service.jar"]

ENV TZ=Asia/Seoul
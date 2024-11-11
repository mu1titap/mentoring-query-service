
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY build/libs/gateway-0.0.1-SNAPSHOT.jar gateway-service.jar

EXPOSE 9300

ENTRYPOINT ["java", "-jar", "mentoring-query-service.jar"]

ENV TZ=Asia/Seoul
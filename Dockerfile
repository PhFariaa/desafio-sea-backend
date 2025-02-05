FROM gradle:jdk21 AS build

WORKDIR /app/src
COPY . /app/src/
RUN gradle build -x test --no-daemon

FROM eclipse-temurin:21-jdk
WORKDIR /app

COPY --from=build /app/src/build/libs/*.jar /app/spring-boot-application.jar

EXPOSE 8080

WORKDIR /app

ENTRYPOINT ["java","-jar","spring-boot-application.jar"]
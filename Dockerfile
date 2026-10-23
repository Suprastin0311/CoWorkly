FROM maven:3.10.0-eclipse-temurin-21-noble AS build
WORKDIR /build
COPY pom.xml ./
COPY src ./src
RUN mvn --batch-mode --no-transfer-progress clean package

FROM eclipse-temurin:21-jre-noble
WORKDIR /app
ENV LANG=C.UTF-8
ENV LC_ALL=C.UTF-8
COPY --from=build --chown=10001:10001 /build/target/CoWorkly.jar ./CoWorkly.jar
RUN mkdir -p /app/logs && chown -R 10001:10001 /app
USER 10001:10001
ENTRYPOINT ["java", "-jar", "/app/CoWorkly.jar"]

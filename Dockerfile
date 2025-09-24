# ====== STAGE 1: BUILD ======
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /src

COPY pom.xml .
COPY src ./src
RUN mvn -DskipTests=true clean package

# ====== STAGE 2: RUNTIME ======
FROM eclipse-temurin-21-jre
WORKDIR /app

COPY --from=build /src/target/app.jar /app/app.jar
ENV JAVA_OPTS="-XX:MaxRAMPercentage=75.0"
CMD ["sh","-c","java $JAVA_OPTS -jar /app/app.jar"]

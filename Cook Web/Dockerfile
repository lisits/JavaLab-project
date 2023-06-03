FROM maven:3.9.2-eclipse-temurin-20-alpine

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline

COPY . .

RUN mvn clean install -DskipTests

CMD mvn spring-boot:run
FROM maven:3.9.6-eclipse-temurin-21-jammy AS build

WORKDIR /app

COPY . .

RUN java -version

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

COPY --from=build app/target/clickbarber-0.0.1-SNAPSHOT.jar /app/clickbarber-0.0.1-SNAPSHOT.jar

EXPOSE 8081

CMD ["java", "-jar", "clickbarber-0.0.1-SNAPSHOT.jar"]
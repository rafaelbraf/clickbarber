FROM maven:3.9.6-eclipse-temurin-21-jammy AS build

WORKDIR /app

COPY . .

RUN mvn -v

RUN java -version

RUN mvn clean package -DskipTests

# Executa o script para extrair a versão e renomear o JAR
RUN chmod +x get_version.sh && ./get_version.sh

FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app
COPY --from=build /app/target/clickbarber.jar /app/clickbarber.jar

EXPOSE 8081

CMD ["java", "-jar", "clickbarber.jar"]
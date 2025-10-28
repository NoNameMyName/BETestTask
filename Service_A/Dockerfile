#FROM maven:3.9.6-eclipse-temurin-17 AS build
#WORKDIR /app
#COPY . .
#RUN mvn clean package -DskipTests
#
#FROM openjdk:17-jdk-slim
#WORKDIR /app
#COPY --from=build /app/target/auth-api-0.0.1-SNAPSHOT.jar app.jar
#ENTRYPOINT ["java", "-jar", "app.jar"]

# Стадия сборки (с Maven)
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
# Копируем pom.xml и загружаем зависимости (для кэширования слоёв)
COPY pom.xml .
RUN mvn dependency:go-offline -B
# Копируем исходный код и собираем
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime-стадия (только Java, используем актуальный образ)
FROM eclipse-temurin:17-jdk
WORKDIR /app
# Копируем собранный JAR из стадии build
COPY --from=build /app/target/auth-api-0.0.1-SNAPSHOT.jar app.jar
# Запуск приложения
ENTRYPOINT ["java", "-jar", "app.jar"]
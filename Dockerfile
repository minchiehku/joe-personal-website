# 第一階段：編譯和打包
FROM maven:3.8.8-eclipse-temurin-17 AS builder

WORKDIR /app
COPY . .
COPY wait-for-db.sh .
RUN chmod +x wait-for-db.sh

# 安裝 mysql-client
RUN apt-get update && apt-get install -y default-mysql-client

RUN mvn clean package -DskipTests

# 第二階段：運行環境
FROM eclipse-temurin:17-jre

WORKDIR /app
COPY --from=builder /app/target/PersonalWebsite-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

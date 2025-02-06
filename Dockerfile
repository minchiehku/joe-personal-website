# 第一階段：編譯和打包
FROM maven:3.8.8-eclipse-temurin-17 AS builder

WORKDIR /app

# 複製專案所有檔案
COPY . .

# 讓 wait-for-db.sh 具有執行權限
RUN chmod +x wait-for-db.sh

# 執行 Maven，打包應用程式
RUN mvn clean package -DskipTests

# 第二階段：運行環境
FROM eclipse-temurin:17-jre

WORKDIR /app

# 從 builder 階段複製打包好的 JAR
COPY --from=builder /app/target/PersonalWebsite-0.0.1-SNAPSHOT.jar app.jar

# 執行 Spring Boot 應用程式
ENTRYPOINT ["java", "-jar", "app.jar"]

#!/bin/bash

# 切換到專案目錄
cd "$(dirname "$0")" || exit 1

# 1. 執行測試
echo "開始進行測試..."
docker-compose -f docker-compose-test.yml up --build --abort-on-container-exit --exit-code-from test
TEST_EXIT_CODE=$?

# 2. 停止並清理測試環境
docker-compose -f docker-compose-test.yml down

if [ $TEST_EXIT_CODE -ne 0 ]; then
  echo "測試失敗，停止流程。"
  exit 1
fi

# 3. 打包專案
echo "測試通過，開始打包..."
mvn clean package -DskipTests
if [ $? -ne 0 ]; then
  echo "打包失敗，停止流程。"
  exit 1
fi

# 4. 建立 Docker 映像檔
echo "開始打包 Docker 映像檔..."
docker build -t personalwebsite-app .
if [ $? -ne 0 ]; then
  echo "Docker 映像檔打包失敗，停止流程。"
  exit 1
fi

echo "流程完成，Docker 映像檔已建立在本機。"

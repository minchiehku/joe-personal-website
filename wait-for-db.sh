#!/bin/bash
set -e

host="$1"
shift

echo "等待資料庫啟動（$host）..."

until mysqladmin ping -h "$host" -u root -pspringboot --silent; do
  echo "資料庫未就緒，等待 2 秒..."
  sleep 2
done

echo "資料庫已啟動，開始執行命令：$*"

exec "$@"




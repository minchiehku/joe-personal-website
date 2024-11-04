FROM ubuntu:latest
LABEL authors="kujoe"

ENTRYPOINT ["top", "-b"]
FROM java:8-jdk

ARG ROOT_DIR=/usr/workspace
ARG WORK_DIR=$ROOT_DIR/linden-honey

ENV SERVER_PORT=8080

COPY . $WORK_DIR
WORKDIR $WORK_DIR

EXPOSE $SERVER_PORT
CMD ["./gradlew", "bootRun"]

FROM java:8-jdk

ENV ROOT_DIR=/usr/workspace
ENV WORK_DIR=$ROOT_DIR/linden-honey
ENV PORT=8080

COPY . $WORK_DIR
WORKDIR $WORK_DIR

EXPOSE 8080
CMD ["./gradlew", "bootRun"]

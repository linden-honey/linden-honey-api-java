# ===============================
# [ Builder image ]
# ===============================
FROM java:8-jdk-alpine AS BUILDER

ARG ROOT_DIR=/workspace
ARG WORK_DIR=$ROOT_DIR/linden-honey

COPY . $WORK_DIR
WORKDIR $WORK_DIR

RUN ./gradlew build -x test

# ===============================
# [ Production image ]
# ===============================
FROM java:8-jre-alpine

LABEL name="linden-honey-spring" \
      maintainer="aliaksandr.babai@gmail.com"

ARG ROOT_DIR=/workspace
ARG WORK_DIR=$ROOT_DIR/linden-honey
ENV SERVER_PORT=8080

WORKDIR $WORK_DIR
COPY --from=BUILDER $WORK_DIR/build/libs/*.jar .

EXPOSE $SERVER_PORT
CMD ["/bin/sh", "-c", "java -jar *.jar"]

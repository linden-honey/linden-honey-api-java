# ===============================
# [ Builder image ]
# ===============================
FROM openjdk:8-jdk-alpine AS BUILDER

ARG ROOT_DIR=/workspace
ARG WORK_DIR=$ROOT_DIR/linden-honey

COPY . $WORK_DIR
WORKDIR $WORK_DIR

RUN ./gradlew build -x test

# ===============================
# [ Production image ]
# ===============================
FROM openjdk:8-jre-alpine

LABEL name="linden-honey-spring" \
      maintainer="aliaksandr.babai@gmail.com"

ARG ROOT_DIR=/workspace
ARG WORK_DIR=$ROOT_DIR/linden-honey

ENV LINDEN_HONEY_APP_PORT=8080 \
    LINDEN_HONEY_APP_PROFILES=prod \
    JAVA_OPTS=""

WORKDIR $WORK_DIR
COPY --from=BUILDER $WORK_DIR/build/libs/*.jar .

EXPOSE $SERVER_PORT
CMD ["/bin/sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/urandom -jar *.jar"]

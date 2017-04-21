FROM java:8-jdk

COPY . /usr/src/linden-honey
WORKDIR /usr/src/linden-honey

EXPOSE 8080
CMD ["./gradlew", "bootRun"]

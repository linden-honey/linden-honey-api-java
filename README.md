# Linden Honey API

> Service with lyrics API powered by Spring Boot

[![build](https://img.shields.io/github/workflow/status/linden-honey/linden-honey-api-java/CI)](https://github.com/linden-honey/linden-honey-api-java/actions?query=workflow%3ACI)
[![version](https://img.shields.io/badge/java->=11-brightgreen.svg?style=flat-square)](https://openjdk.java.net/)
[![coverage](https://img.shields.io/codecov/c/github/linden-honey/linden-honey-api-java)](https://codecov.io/github/linden-honey/linden-honey-api-java)
[![tag](https://img.shields.io/github/tag/linden-honey/linden-honey-api-java.svg)](https://github.com/linden-honey/linden-honey-api-java/tags)

## Technologies

- [Java](https://openjdk.java.net/)
- [Gradle](https://gradle.org/)
- [Spring](https://spring.io/)
- [PostgreSQL](https://www.postgresql.org/)

## Usage

### Local

Build application artifacts:

```bash
./gradlew build
```

Build application artifacts (without tests):

```bash
./gradlew build -x test
```

Run tests:

```bash
./gradlew test
```

Run application:

```bash
./gradlew bootRun
```

### Docker

Require already built application artifacts

Bootstrap full project using docker-compose:

```bash
docker-compose up
```

Bootstrap project excluding some services using docker-compose:

```bash
docker-compose up --scale [SERVICE=0...]
```

Stop and remove containers, networks, images:

```bash
docker-compose down
```

## Application instance

[https://linden-honey-api-java.herokuapp.com](https://linden-honey-api-java.herokuapp.com)

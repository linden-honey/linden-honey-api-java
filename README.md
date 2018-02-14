# Linden Honey Spring

> RESTful Web Service powered by Spring Boot

[![java version][java-image]][java-url]
[![build status][travis-image]][travis-url]
[![release][release-image]][release-url]
[![license][license-image]][license-url]

[java-image]: https://img.shields.io/badge/java-%3E%3D8-brightgreen.svg?style=flat-square
[java-url]: http://www.oracle.com/technetwork/java/javase/downloads/index.html
[release-image]: https://img.shields.io/github/release/linden-honey/linden-honey-spring.svg?style=flat-square
[release-url]: https://github.com/linden-honey/linden-honey-spring/releases
[travis-image]: https://img.shields.io/travis/linden-honey/linden-honey-spring/master.svg?style=flat-square
[travis-url]: https://travis-ci.org/linden-honey/linden-honey-spring
[license-image]: https://img.shields.io/github/license/mashape/apistatus.svg?style=flat-square
[license-url]: https://github.com/linden-honey/linden-honey-spring/blob/master/LICENSE

REST API for the lyrics of __Yegor Letov__ and his post-punk/psychedelic rock band __Grazhdanskaya Oborona (Civil Defense)__

## Technologies

* [Gradle](https://gradle.org/)
* [Spring](https://spring.io/)
* [MongoDB](https://www.mongodb.com/)

## Usage

### Local

The following environment variables should be exported before running:
* `LINDEN_HONEY_DB_URL`

Run application:
```bash
$ ./gradlew bootRun
```

Run tests (required clean pre-configured database):
```bash
$ ./gradlew test
```

Build application artifact (with tests - required clean pre-configured database):
```bash
$ ./gradlew build
```

Build application artifacts (without tests):
```bash
$ ./gradlew build -x test
```

### Docker

Bootstrap project using docker-compose:
```bash
$ docker-compose up
```

Stop and remove containers, networks, and volumes:
```bash
$ docker-compose down -v
```

## Application instance

[https://linden-honey-spring.herokuapp.com](https://linden-honey-spring.herokuapp.com)

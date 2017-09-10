# Linden Honey Spring

> RESTful Web Service powered by Spring Boot

[![java version][java-image]][java-url]
[![build status][travis-image]][travis-url]
[![release][release-image]][release-url]
[![downloads][downloads-image]][release-url]
[![license][license-image]][license-url]

[java-image]: https://img.shields.io/badge/java-%3E%3D8-brightgreen.svg?style=flat-square
[java-url]: http://www.oracle.com/technetwork/java/javase/downloads/index.html
[release-image]: https://img.shields.io/github/release/linden-honey/linden-honey-book.svg?style=flat-square
[release-url]: https://github.com/linden-honey/linden-honey-book/releases
[downloads-image]: https://img.shields.io/github/downloads/linden-honey/linden-honey-spring/latest/total.svg?style=flat-square
[downloads-url]: https://github.com/linden-honey/linden-honey-book/releases
[travis-image]: https://img.shields.io/travis/linden-honey/linden-honey-book/master.svg?style=flat-square
[travis-url]: https://travis-ci.org/linden-honey/linden-honey-book
[license-image]: https://img.shields.io/github/license/mashape/apistatus.svg?style=flat-square
[license-url]: https://github.com/linden-honey/linden-honey-book/blob/master/LICENSE

REST API for the lyrics of __Yegor Letov__ and his post-punk/psychedelic rock band __Grazhdanskaya Oborona (Civil Defense)__

## Technologies

* [Gradle](https://gradle.org/)
* [Spring](https://spring.io/)
* [PostgreSQL](https://www.postgresql.org/)

## Usage

### Local

The following environment variables should be exported before running:
* `LINDEN_HONEY_DB_URL`

Run application:
```
./gradlew bootRun
```

Run tests (required clean pre-configured database):
```
./gradlew test
```

Build application artifact (with tests - required clean pre-configured database):
```
./gradlew build
```

Build application artifacts (without tests):
```
./gradlew build -x test
```

### Docker

Bootstrap project using docker-compose:
```
docker-compose up
```

Stop and remove containers, networks, images, and volumes:
```
docker-compose down
```

## Application instance

[https://linden-honey-spring.herokuapp.com](https://linden-honey-spring.herokuapp.com)

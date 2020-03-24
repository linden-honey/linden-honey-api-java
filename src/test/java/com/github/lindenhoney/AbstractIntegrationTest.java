package com.github.lindenhoney;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.io.File;

@SpringBootTest
@Slf4j
public abstract class AbstractIntegrationTest {

    public static final String DB_SERVICE_NAME = "db";
    public static final int DB_SERVICE_PORT = 5432;

    public static final DockerComposeContainer ENVIRONMENT = createEnvironment();

    static {
        ENVIRONMENT.start();
        setDbProps();
    }

    public static DockerComposeContainer createEnvironment() {
        return new DockerComposeContainer<>(new File("./docker-compose.yml"))
                .withScaledService("app", 0)
                .withLogConsumer(DB_SERVICE_NAME, new Slf4jLogConsumer(log))
                .withExposedService(
                        DB_SERVICE_NAME,
                        DB_SERVICE_PORT,
                        Wait.forLogMessage(".*database system is ready to accept connections.*", 1)
                );
    }

    public static void setDbProps() {
        final String host = ENVIRONMENT.getServiceHost(DB_SERVICE_NAME, DB_SERVICE_PORT);
        final Integer port = ENVIRONMENT.getServicePort(DB_SERVICE_NAME, 5432);
        System.setProperty("spring.datasource.url", dbUri(host, port));
    }

    public static String dbUri(String host, Integer port) {
        return String.format("jdbc:postgresql://%s:%d/linden-honey?user=linden-honey&password=linden-honey", host, port);
    }
}

package com.github.lindenhoney;

import com.github.lindenhoney.config.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@EnableReactiveMongoRepositories(basePackages = {"com.github.lindenhoney.repository"})
@EnableConfigurationProperties({ApplicationProperties.class})
@SpringBootApplication(scanBasePackages = {"com.github.lindenhoney"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

package com.github.lindenhoney;

import com.github.lindenhoney.config.LindenHoneyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@EnableReactiveMongoRepositories(basePackages = {"com.github.lindenhoney.repository"})
@EnableConfigurationProperties({LindenHoneyProperties.class})
@SpringBootApplication(scanBasePackages = {"com.github.lindenhoney"})
public class LindenHoneyApplication {

    public static void main(String[] args) {
        SpringApplication.run(LindenHoneyApplication.class, args);
    }
}

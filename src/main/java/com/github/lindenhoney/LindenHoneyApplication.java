package com.github.lindenhoney;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication(scanBasePackages = "com.github.lindenhoney")
@EnableReactiveMongoRepositories(basePackages = "com.github.lindenhoney.repository")
public class LindenHoneyApplication {

    public static void main(String[] args) {
        SpringApplication.run(LindenHoneyApplication.class, args);
    }
}

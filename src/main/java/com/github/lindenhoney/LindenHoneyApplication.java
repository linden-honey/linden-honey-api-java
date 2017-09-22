package com.github.lindenhoney;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.github.lindenhoney")
@EnableJpaRepositories(basePackages = "com.github.lindenhoney.repository")
public class LindenHoneyApplication {

    public static void main(String[] args) {
        SpringApplication.run(LindenHoneyApplication.class, args);
    }
}

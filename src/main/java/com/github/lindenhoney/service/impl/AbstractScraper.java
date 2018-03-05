package com.github.lindenhoney.service.impl;

import com.github.lindenhoney.config.LindenHoneyProperties;
import com.github.lindenhoney.service.Scraper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractScraper implements Scraper {

    protected final LindenHoneyProperties.Scrapers.Scraper properties;
    protected final Validator validator;
    protected final WebClient client;

    protected AbstractScraper(LindenHoneyProperties.Scrapers.Scraper properties, Validator validator) {
        this.properties = properties;
        this.validator = validator;
        this.client = WebClient.builder()
                .baseUrl(properties.getBaseUrl())
                .build();
    }

    protected <T> boolean validate(T bean) {
        final Set<ConstraintViolation<T>> violations = validator.validate(bean);
        final boolean isValid = violations.isEmpty();
        if (!isValid) {
            final List<String> messages = violations.stream()
                    .map(v -> String.format("%s %s, but have value \"%s\"", v.getPropertyPath(), v.getMessage(), v.getInvalidValue()))
                    .collect(Collectors.toList());
            log.warn("{} validation failed: {}", bean, messages);
        }
        return isValid;
    }
}

package com.github.lindenhoney.migration;

import com.github.lindenhoney.config.ApplicationProperties;
import com.github.mongobee.Mongobee;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Optional;

@ConditionalOnClass(value = Mongobee.class)
@ConditionalOnProperty(name = "linden-honey.db.migration.enabled", havingValue = "true")
@Configuration
public class MigrationsConfiguration {

    private final ApplicationProperties.Database properties;
    private final Environment environment;
    private final MongoTemplate mongoTemplate;

    public MigrationsConfiguration(ApplicationProperties properties, Environment environment, MongoTemplate mongoTemplate) {
        this.properties = properties.getDb();
        this.environment = environment;
        this.mongoTemplate = mongoTemplate;
    }

    @Bean
    public Mongobee mongobee() {
        return new Mongobee(properties.getUri())
                .setEnabled(properties.getMigration().isEnabled())
                .setSpringEnvironment(environment)
                .setMongoTemplate(mongoTemplate)
                .setChangeLogsScanPackage(
                        Optional.ofNullable(properties.getMigration().getScanPackage())
                                .orElseGet(() -> getClass().getPackage().getName())
                );
    }
}

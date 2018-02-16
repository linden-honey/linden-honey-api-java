package com.github.lindenhoney.migration;

import com.github.lindenhoney.config.LindenHoneyProperties;
import com.github.mongobee.Mongobee;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@ConditionalOnClass(value = Mongobee.class)
@ConditionalOnProperty(name = "linden-honey.db.migration.enabled", havingValue = "true")
@Configuration
public class MigrationsConfiguration {

    private final LindenHoneyProperties.Database properties;

    public MigrationsConfiguration(LindenHoneyProperties properties) {
        this.properties = properties.getDb();
    }

    @Bean
    public Mongobee mongobee() {
        return new Mongobee(properties.getUri())
                .setChangeLogsScanPackage(
                        Optional.ofNullable(properties.getMigration().getScanPackage())
                                .orElseGet(() -> getClass().getPackage().getName())
                );
    }
}

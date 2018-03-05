package com.github.lindenhoney.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.Duration;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "linden-honey")
public class LindenHoneyProperties {

    @Valid
    private final Application app = new Application();

    @Valid
    private final Database db = new Database();

    @Valid
    private final Scrapers scrapers = new Scrapers();

    @Getter
    @Setter
    public static class Application {

        private String name;

        @NotNull
        @PositiveOrZero
        private Integer port;
    }

    @Getter
    @Setter
    public static class Database {

        @NotNull
        private String uri;

        @Valid
        private final Migration migration = new Migration();

        @Getter
        @Setter
        public static class Migration {

            private boolean enabled;
            private String scanPackage;
            private String initialDataUrl;
        }
    }

    @Getter
    @Setter
    public static class Scrapers {

        private boolean enabled;

        @Valid
        private final Scraper grob = new Scraper();

        @Getter
        @Setter
        public static class Scraper {

            private boolean enabled;

            @NotNull
            private String baseUrl;

            @Valid
            private final Retry retry = new Retry();

            @Getter
            @Setter
            public static class Retry {
                private int maxRetries;
                private Duration firstBackoff;
                private Duration maxBackoff;
            }
        }
    }
}

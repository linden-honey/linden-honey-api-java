package com.github.lindenhoney.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
@ConfigurationProperties(prefix = "linden-honey")
public class LindenHoneyProperties {

    private final Application app = new Application();
    private final Database db = new Database();

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
        private final Migration migration = new Migration();

        @Getter
        @Setter
        public static class Migration {

            private boolean enabled;
            private String scanPackage;
        }
    }
}

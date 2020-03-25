package com.github.lindenhoney.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.net.URL;

@Validated
@ConfigurationProperties("application.data.loaders.remote")
@Data
public class RemoteDataLoaderProperties {
    @NotNull
    private URL url;
}

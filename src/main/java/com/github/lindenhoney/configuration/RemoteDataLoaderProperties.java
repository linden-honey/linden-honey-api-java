package com.github.lindenhoney.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.net.URL;

@Validated
@ConfigurationProperties("application.data.loaders.remote")
@Data
public class RemoteDataLoaderProperties {
    private URL url;
}

package com.github.lindenhoney.configuration;

import com.github.lindenhoney.service.DataLoader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

@ConditionalOnProperty("application.data.loaders.enabled")
@EnableConfigurationProperties(RemoteDataLoaderProperties.class)
@Configuration
@Slf4j
@RequiredArgsConstructor
public class DataLoadersConfiguration {

    private final List<DataLoader> dataLoaders;

    @PostConstruct
    protected void loadData() {
        log.debug("Data loading - started");
        log.debug("{} data loader(s) are pending", dataLoaders.size());
        dataLoaders.forEach(loader -> {
            final String loaderName = loader.getClass().getSimpleName();
            log.debug("{} - started", loaderName);
            try {
                loader.loadData();
            } catch (Exception e) {
                log.error("{} - error happened during data loading", loaderName,  e);
            }
            log.debug("{} - finished", loaderName);
        });
        log.debug("Data loading - finished");
    }

}

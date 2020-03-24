package com.github.lindenhoney.service;

import com.github.lindenhoney.configuration.DataLoadersConfiguration;
import com.github.lindenhoney.configuration.RemoteDataLoaderProperties;
import com.github.lindenhoney.entity.SongEntity;
import com.github.lindenhoney.repository.SongRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@ConditionalOnBean(DataLoadersConfiguration.class)
@Service
@Slf4j
public class RemoteDataLoader implements DataLoader {

    private final SongRepository repository;
    private final WebClient client;

    public RemoteDataLoader(RemoteDataLoaderProperties properties, SongRepository repository) {
        this.repository = repository;
        this.client = WebClient
                .builder()
                .baseUrl(properties.getUrl().toString())
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(cfg -> cfg.defaultCodecs().jackson2JsonDecoder(
                                new Jackson2JsonDecoder(
                                        Jackson2ObjectMapperBuilder.json().build(),
                                        MediaType.APPLICATION_JSON,
                                        MediaType.TEXT_PLAIN
                                )
                        ))
                        .build())
                .build();
    }

    @Override
    public void loadData() {
        final List<SongEntity> entities = client.get()
                .exchange()
                .flatMapMany(res -> res.bodyToFlux(SongEntity.class))
                .toStream()
                .collect(Collectors.toList());
        repository.saveAll(entities);
    }
}

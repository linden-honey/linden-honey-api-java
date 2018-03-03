package com.github.lindenhoney.service.impl;

import com.github.lindenhoney.domain.Song;
import com.github.lindenhoney.domain.SongPreview;
import com.github.lindenhoney.util.GrobParser;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.retry.Retry;

import javax.validation.Validator;
import java.net.ConnectException;
import java.nio.charset.Charset;
import java.time.Duration;

@Service
public class GrobScraper extends AbstractScraper {

    private static final String SOURCE_CHARSET = "windows-1251";

    //TODO add support for configuration per implementation
    //TODO add support for scraper on/off flag per implementation

    public GrobScraper(Validator validator) {
        super(validator, WebClient.builder()
                .baseUrl("http://www.gr-oborona.ru/")
                .build());
    }

    @Override
    public Flux<Song> fetchSongs() {
        return fetchPreviews()
                .map(SongPreview::getId)
                .flatMap(this::fetchSong);
    }

    protected Flux<SongPreview> fetchPreviews() {
        return client.get()
                .uri("texts")
                .exchange()
                .flatMap(response -> response.bodyToMono(byte[].class))
                .map(bytes -> new String(bytes, Charset.forName(SOURCE_CHARSET)))//TODO wrap parser call as body extracto
                .flatMapMany(html -> Flux.fromStream(GrobParser.parsePreviews(html)))
                .filter(this::validate);
    }

    protected Mono<Song> fetchSong(Long id) {
        return client.get()
                .uri(builder -> builder
                        .path("text_print.php")
                        .queryParam("area", "go_texts")
                        .queryParam("id", id)
                        .build())
                .exchange()
                .retryWhen(Retry
                        .anyOf(ConnectException.class)
                        .retryMax(5)
                        .exponentialBackoff(Duration.ofSeconds(1), Duration.ofSeconds(6)))
                .flatMap(response -> response.bodyToMono(byte[].class))
                .map(bytes -> new String(bytes, Charset.forName(SOURCE_CHARSET)))//TODO wrap parser call as body extractor
                .flatMap(html -> Mono.justOrEmpty(GrobParser.parseSong(html)))
                .filter(this::validate);
    }
}

package com.github.lindenhoney.service;

import com.github.lindenhoney.domain.Song;
import com.github.lindenhoney.domain.SongPreview;
import com.github.lindenhoney.util.GrobParser;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Validator;

@Service
public class GrobScraper implements Scraper {

    //TODO Global - fix response encoding

    private final Validator validator;
    private final WebClient client;

    public GrobScraper(Validator validator) {
        this.validator = validator;
        this.client = WebClient.builder()
                .baseUrl("http://www.gr-oborona.ru/")
                .build();
    }

    @Override
    public Flux<SongPreview> fetchPreviews() {
        return client.get()
                .uri("texts")
                .exchange()
                .retry(5)//TODO make retry configurable
                .flatMap(response -> response.bodyToMono(String.class))//TODO wrap parser call as body extracto
                .flatMapMany(html -> Flux.fromStream(GrobParser.parsePreviews(html)))
                .filter(preview -> validator.validate(preview).isEmpty());
    }

    @Override
    public Flux<Song> fetchSongs() {
        return fetchPreviews()
                .map(SongPreview::getId)
                .flatMap(this::fetchSong);
    }

    @Override
    public Mono<Song> fetchSong(Long id) {
        return client.get()
                .uri(builder -> builder
                        .path("text_print.php")
                        .queryParam("area", "go_texts")
                        .queryParam("id", id)
                        .build())
                .exchange()
                .retry(5)//TODO make retry configurable
                .flatMap(response -> response.bodyToMono(String.class))//TODO wrap parser call as body extractor
                .flatMap(html -> Mono.justOrEmpty(GrobParser.parseSong(html)))
                .filter(song -> validator.validate(song).isEmpty());
    }
}

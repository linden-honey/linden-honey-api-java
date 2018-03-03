package com.github.lindenhoney.service;

import com.github.lindenhoney.domain.Song;
import reactor.core.publisher.Flux;

@FunctionalInterface
public interface Scraper {
    Flux<Song> fetchSongs();
}

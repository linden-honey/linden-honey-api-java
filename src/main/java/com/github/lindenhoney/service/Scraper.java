package com.github.lindenhoney.service;

import com.github.lindenhoney.domain.Song;
import com.github.lindenhoney.domain.SongPreview;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface Scraper {
    Flux<SongPreview> fetchPreviews();

    Flux<Song> fetchSongs();

    Mono<Song> fetchSong(Long id);
}

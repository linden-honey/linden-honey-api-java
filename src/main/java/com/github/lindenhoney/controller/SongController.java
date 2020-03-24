package com.github.lindenhoney.controller;

import com.github.lindenhoney.domain.Chunk;
import com.github.lindenhoney.domain.Quote;
import com.github.lindenhoney.domain.Song;
import com.github.lindenhoney.domain.Verse;
import com.github.lindenhoney.mapper.QuoteMapper;
import com.github.lindenhoney.mapper.SongMapper;
import com.github.lindenhoney.mapper.VerseMapper;
import com.github.lindenhoney.repository.QuoteRepository;
import com.github.lindenhoney.repository.SongRepository;
import com.github.lindenhoney.repository.VerseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.lindenhoney.util.PageableUtil.createPageable;

@RestController
@RequestMapping("/songs")
@RequiredArgsConstructor
public class SongController {

    private final QuoteRepository quoteRepository;
    private final QuoteMapper quoteMapper;

    private final VerseRepository verseRepository;
    private final VerseMapper verseMapper;

    private final SongRepository songRepository;
    private final SongMapper songMapper;

    @GetMapping("/search/random")
    public ResponseEntity<Song> getRandomSong() {
        return ResponseEntity.of(songRepository
                .findRandomSong()
                .map(songMapper::toDomain)
        );
    }

    @GetMapping("/search/by-title")
    public ResponseEntity<Chunk<Song>> findSongsByTitle(
            @RequestParam String title,
            @PositiveOrZero @RequestParam(defaultValue = Chunk.DEFAULT_LIMIT) int limit,
            @PositiveOrZero @RequestParam(defaultValue = Chunk.DEFAULT_OFFSET) int offset,
            @NotBlank @RequestParam(defaultValue = "title") String sortBy,
            @NotBlank @RequestParam(defaultValue = Chunk.DEFAULT_SORT_ORDER) String sortOrder
    ) {
        return ResponseEntity.ok(
                songMapper.toDomain(
                        songRepository.findSongsByTitleContainingIgnoreCase(
                                title,
                                createPageable(limit, offset, sortBy, sortOrder)
                        )
                )
        );
    }

    @GetMapping("/search/by-phrase")
    public ResponseEntity<Chunk<Song>> findSongsByPhrase(
            @RequestParam String phrase,
            @PositiveOrZero @RequestParam(defaultValue = Chunk.DEFAULT_LIMIT) int limit,
            @PositiveOrZero @RequestParam(defaultValue = Chunk.DEFAULT_OFFSET) int offset,
            @NotBlank @RequestParam(defaultValue = "title") String sortBy,
            @NotBlank @RequestParam(defaultValue = Chunk.DEFAULT_SORT_ORDER) String sortOrder
    ) {
        return ResponseEntity.ok(
                songMapper.toDomain(
                        songRepository.findDistinctSongsByVersesQuotesPhraseContainingIgnoreCase(
                                phrase,
                                createPageable(limit, offset, sortBy, sortOrder)
                        )
                )
        );
    }

    @GetMapping
    public ResponseEntity<Chunk<Song>> getAllSongs(
            @PositiveOrZero @RequestParam(defaultValue = Chunk.DEFAULT_LIMIT) int limit,
            @PositiveOrZero @RequestParam(defaultValue = Chunk.DEFAULT_OFFSET) int offset,
            @NotBlank @RequestParam(defaultValue = "title") String sortBy,
            @NotBlank @RequestParam(defaultValue = Chunk.DEFAULT_SORT_ORDER) String sortOrder
    ) {
        return ResponseEntity.ok(
                songMapper.toDomain(
                        songRepository.findAll(
                                createPageable(limit, offset, sortBy, sortOrder)
                        )
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Song> getRandomSong(@PathVariable Integer id) {
        return ResponseEntity.of(songRepository
                .findById(id)
                .map(songMapper::toDomain)
        );
    }

    @GetMapping("/{id}/quotes")
    public ResponseEntity<List<Quote>> getQuotesFromSong(@PathVariable Integer id) {
        return ResponseEntity.of(
                songRepository.findById(id)
                        .map(songMapper::toDomain)
                        .map(song -> song.getVerses()
                                .stream()
                                .flatMap(verse -> verse.getQuotes().stream())
                                .collect(Collectors.toList())
                        )
        );
    }

    @GetMapping("/{id}/quotes/search/random")
    public ResponseEntity<Quote> getRandomQuoteFromSong(@PathVariable Integer id) {
        return ResponseEntity.of(quoteRepository
                .findRandomQuoteFromSong(id)
                .map(quoteMapper::toDomain)
        );
    }

    @GetMapping("/{id}/quotes/search/by-phrase")
    public ResponseEntity<List<Quote>> getQuotesFromSong(
            @PathVariable Integer id,
            @RequestParam String phrase
    ) {
        final List<Quote> quotes = quoteRepository.findQuotesByPhraseFromSong(id, phrase)
                .stream()
                .map(quoteMapper::toDomain)
                .collect(Collectors.toList());
        return quotes.isEmpty()
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(quotes);
    }

    @GetMapping("/{id}/verses")
    public ResponseEntity<List<Verse>> getVersesFromSong(@PathVariable Integer id) {
        return ResponseEntity.of(
                songRepository.findById(id)
                        .map(songMapper::toDomain)
                        .map(Song::getVerses)
        );
    }

    @GetMapping("/{id}/verses/search/random")
    public ResponseEntity<Verse> getRandomVerseFromSong(@PathVariable Integer id) {
        return ResponseEntity.of(verseRepository
                .findRandomVerseFromSong(id)
                .map(verseMapper::toDomain)
        );
    }
}

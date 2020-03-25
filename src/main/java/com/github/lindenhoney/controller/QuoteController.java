package com.github.lindenhoney.controller;

import com.github.lindenhoney.domain.Chunk;
import com.github.lindenhoney.domain.Quote;
import com.github.lindenhoney.mapper.QuoteMapper;
import com.github.lindenhoney.repository.QuoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.PositiveOrZero;

import static com.github.lindenhoney.util.PageableUtil.createPageable;

@RestController
@RequestMapping("/quotes")
@RequiredArgsConstructor
public class QuoteController {

    private final QuoteRepository repository;
    private final QuoteMapper mapper;

    @GetMapping("/search/random")
    public ResponseEntity<Quote> getRandomQuote() {
        return ResponseEntity.of(repository
                .findRandomQuote()
                .map(mapper::toDomain)
        );
    }

    @GetMapping("/search/by-phrase")
    public ResponseEntity<Chunk<Quote>> findQuotesByPhrase(
            @RequestParam String phrase,
            @PositiveOrZero @RequestParam(defaultValue = Chunk.DEFAULT_LIMIT) int limit,
            @PositiveOrZero @RequestParam(defaultValue = Chunk.DEFAULT_OFFSET) int offset,
            @RequestParam(defaultValue = "phrase") String sortBy@RequestParam(defaultValue = Chunk.DEFAULT_SORT_ORDER) String sortOrder
    ) {
        return ResponseEntity.ok(
                mapper.toDomain(
                        repository.findQuotesByPhrase(
                                phrase,
                                createPageable(limit, offset, sortBy, sortOrder)
                        )
                )
        );
    }
}

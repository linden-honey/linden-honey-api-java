package com.github.lindenhoney.controller;

import com.github.lindenhoney.domain.Quote;
import com.github.lindenhoney.mapper.QuoteMapper;
import com.github.lindenhoney.repository.QuoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/quotes")
@RequiredArgsConstructor
public class QuoteController {

    private final QuoteRepository repository;
    private final QuoteMapper quoteMapper;

    @GetMapping("/search/random")
    public ResponseEntity<Quote> getRandomQuote() {
        return ResponseEntity.of(repository
                .findRandomQuote()
                .map(quoteMapper::mapToDomain)
        );
    }
}

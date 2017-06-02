package com.github.alebabai.lindenhoney.controller;

import com.github.alebabai.lindenhoney.domain.Quote;
import com.github.alebabai.lindenhoney.repository.QuoteRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/quotes/search")
public class QuoteController {

    private final QuoteRepository repository;

    public QuoteController(QuoteRepository repository) {
        this.repository = repository;
    }

    @GetMapping("random")
    public Quote getRandonQuote() {
        return repository.findRandomQuote();
    }
}

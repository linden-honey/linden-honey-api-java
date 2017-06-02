package com.github.alebabai.lindenhoney.controller;

import com.github.alebabai.lindenhoney.domain.Verse;
import com.github.alebabai.lindenhoney.repository.VerseRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/verses/search")
public class VerseController {

    private final VerseRepository repository;

    public VerseController(VerseRepository repository) {
        this.repository = repository;
    }

    @GetMapping("random")
    public Verse getRandonQuote() {
        return repository.findRandomVerse();
    }
}

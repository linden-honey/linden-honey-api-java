package com.github.alebabai.lindenhoney.controller;

import com.github.alebabai.lindenhoney.domain.Verse;
import com.github.alebabai.lindenhoney.repository.VerseRepository;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "api/verses/search", produces = MediaTypes.HAL_JSON_VALUE)
public class VerseController {

    private final VerseRepository repository;

    public VerseController(VerseRepository repository) {
        this.repository = repository;
    }

    @GetMapping("random")
    public Resource<Verse> getRandonQuote() {
        final Verse verse = repository.findRandomVerse();
        return new Resource<>(verse, linkTo(methodOn(this.getClass()).getRandonQuote()).withSelfRel());
    }
}

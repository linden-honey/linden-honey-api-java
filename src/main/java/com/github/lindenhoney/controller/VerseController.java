package com.github.lindenhoney.controller;

import com.github.lindenhoney.domain.Verse;
import com.github.lindenhoney.repository.VerseRepository;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/verses", produces = MediaTypes.HAL_JSON_VALUE)
@ExposesResourceFor(Verse.class)
public class VerseController {

    private final VerseRepository repository;

    public VerseController(VerseRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @ResponseBody
    public ResourceSupport getVersesResource() {
        final ResourceSupport resource = new ResourceSupport();
        resource.add(
                linkTo(methodOn(this.getClass()).getSearchResource()).withRel("search")
        );
        return resource;
    }

    @GetMapping("/search")
    @ResponseBody
    public ResourceSupport getSearchResource() {
        final ResourceSupport resource = new ResourceSupport();
        resource.add(
                linkTo(methodOn(this.getClass()).getRandomVerse()).withRel("random"),
                linkTo(methodOn(this.getClass()).getSearchResource()).withSelfRel()
        );
        return resource;
    }

    @GetMapping("/search/random")
    @ResponseBody
    public Resource<Verse> getRandomVerse() {
        final Verse verse = repository.findRandomVerse();
        return new Resource<>(verse, linkTo(methodOn(this.getClass()).getRandomVerse()).withSelfRel());
    }
}

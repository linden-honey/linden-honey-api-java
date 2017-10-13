package com.github.lindenhoney.controller;

import com.github.lindenhoney.domain.Quote;
import com.github.lindenhoney.repository.QuoteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/quotes", produces = MediaTypes.HAL_JSON_VALUE)
@ExposesResourceFor(Quote.class)
public class QuoteController {

    private final QuoteRepository repository;
    private final PagedResourcesAssembler<Quote> asm;

    public QuoteController(QuoteRepository repository, PagedResourcesAssembler<Quote> asm) {
        this.repository = repository;
        this.asm = asm;
    }


    @GetMapping
    public ResourceSupport getQuotesResource() {
        final ResourceSupport resource = new ResourceSupport();
        resource.add(
                linkTo(methodOn(this.getClass()).getSearchResource()).withRel("search")
        );
        return resource;
    }

    @GetMapping("/search")
    public ResourceSupport getSearchResource() {
        final ResourceSupport resource = new ResourceSupport();
        resource.add(
                linkTo(methodOn(this.getClass()).getRandomQuote()).withRel("random"),
                linkTo(methodOn(this.getClass()).findQuotesByPhrase(null, null)).withRel("by-phrase"),
                linkTo(methodOn(this.getClass()).getSearchResource()).withSelfRel()
        );
        return resource;
    }

    @GetMapping("/search/random")
    public Resource<Quote> getRandomQuote() {
        final Quote quote = repository.findRandomQuote();
        return new Resource<>(quote, linkTo(methodOn(this.getClass()).getRandomQuote()).withSelfRel());
    }

    @GetMapping("/search/by-phrase")
    public PagedResources<Resource<Quote>> findQuotesByPhrase(@RequestParam("phrase") String phrase,
                                                              Pageable pageable) {
        final Page<Quote> page = repository.findQuotesByPhrase(phrase, pageable);
        final Link selfLink = linkTo(methodOn(this.getClass()).findQuotesByPhrase(phrase, pageable)).withSelfRel();
        return asm.toResource(page, selfLink);
    }
}

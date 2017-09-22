package com.github.lindenhoney.controller;

import com.github.lindenhoney.domain.Quote;
import com.github.lindenhoney.repository.QuoteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RepositoryRestController
@RequestMapping(path = "/quotes/search")
public class QuoteController {

    private final QuoteRepository repository;
    private final PagedResourcesAssembler<Quote> asm;

    public QuoteController(QuoteRepository repository, PagedResourcesAssembler<Quote> asm) {
        this.repository = repository;
        this.asm = asm;
    }

    @GetMapping("random")
    @ResponseBody
    public Resource<Quote> getRandomQuote() {
        final Quote quote = repository.findRandomQuote();
        return new Resource<>(quote, linkTo(methodOn(this.getClass()).getRandomQuote()).withSelfRel());
    }

    @GetMapping("by-phrase")
    @ResponseBody
    public PagedResources<Resource<Quote>> findQuotesByPhrase(@RequestParam("phrase") String phrase,
                                                              Pageable pageable) {
        final Page<Quote> page = repository.findQuotesByPhraseContainingIgnoreCase(phrase, pageable);
        final Link selfLink = linkTo(methodOn(this.getClass()).findQuotesByPhrase(phrase, pageable)).withSelfRel();
        return asm.toResource(page, selfLink);
    }
}

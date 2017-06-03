package com.github.alebabai.lindenhoney.controller;

import com.github.alebabai.lindenhoney.domain.Quote;
import com.github.alebabai.lindenhoney.repository.QuoteRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RepositoryRestController
@RequestMapping(path = "api/quotes/search")
public class QuoteController {

    private final QuoteRepository repository;

    public QuoteController(QuoteRepository repository) {
        this.repository = repository;
    }

    @GetMapping("random")
    @ResponseBody
    public Resource<Quote> getRandonQuote() {
        final Quote quote = repository.findRandomQuote();
        return new Resource<>(quote, linkTo(methodOn(this.getClass()).getRandonQuote()).withSelfRel());
    }
}

package com.github.alebabai.lindenhoney.controller;

import com.github.alebabai.lindenhoney.domain.Quote;
import com.github.alebabai.lindenhoney.domain.Song;
import com.github.alebabai.lindenhoney.domain.Verse;
import com.github.alebabai.lindenhoney.repository.QuoteRepository;
import com.github.alebabai.lindenhoney.repository.VerseRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RepositoryRestController
@ExposesResourceFor(Song.class)
public class SongController {

    private final QuoteRepository quoteRepository;
    private final VerseRepository verseRepository;

    public SongController(QuoteRepository quoteRepository, VerseRepository verseRepository) {
        this.quoteRepository = quoteRepository;
        this.verseRepository = verseRepository;
    }

    @GetMapping("/songs/{songId}/quotes/search/random")
    @ResponseBody
    public Resource<Quote> getRandomQuoteFromSong(@PathVariable("songId") Integer songId) {
        final Quote quote = quoteRepository.findRandomQuoteFromSong(songId);
        return new Resource<>(quote, linkTo(methodOn(this.getClass()).getRandomQuoteFromSong(songId)).withSelfRel());
    }

    @GetMapping("/songs/{songId}/verses/search/random")
    @ResponseBody
    public Resource<Verse> getRandomVerseFromSong(@PathVariable("songId") Integer songId) {
        final Verse verse = verseRepository.findRandomVerseFromSong(songId);
        return new Resource<>(verse, linkTo(methodOn(this.getClass()).getRandomVerseFromSong(songId)).withSelfRel());
    }
}

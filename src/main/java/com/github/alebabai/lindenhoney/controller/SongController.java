package com.github.alebabai.lindenhoney.controller;

import com.github.alebabai.lindenhoney.domain.Quote;
import com.github.alebabai.lindenhoney.domain.Song;
import com.github.alebabai.lindenhoney.domain.Verse;
import com.github.alebabai.lindenhoney.repository.QuoteRepository;
import com.github.alebabai.lindenhoney.repository.VerseRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RepositoryRestController
@ExposesResourceFor(Song.class)
public class SongController {

    private final QuoteRepository quoteRepository;
    private final VerseRepository verseRepository;
    private final EntityLinks entityLinks;

    public SongController(QuoteRepository quoteRepository, VerseRepository verseRepository, EntityLinks entityLinks) {
        this.quoteRepository = quoteRepository;
        this.verseRepository = verseRepository;
        this.entityLinks = entityLinks;
    }

    @GetMapping("/songs/{songId}/quotes")
    @ResponseBody
    public Resources<Quote> getAllQuotesFromSong(@PathVariable("songId") Integer songId) {
        final List<Quote> quotes = Optional.ofNullable(quoteRepository.findAllQuotesFromSong(songId))
                .filter(quoteList -> !quoteList.isEmpty())
                .orElseThrow(ResourceNotFoundException::new);
        return new Resources<>(
                quotes,
                entityLinks.linkForSingleResource(Song.class, songId).withRel("song"),
                linkTo(methodOn(this.getClass()).getAllQuotesFromSong(songId)).withSelfRel()
        );
    }

    @GetMapping("/songs/{songId}/verses")
    @ResponseBody
    public Resources<Verse> getAllVersesFromSong(@PathVariable("songId") Integer songId) {
        final List<Verse> verses = Optional.ofNullable(verseRepository.findAllVersesFromSong(songId))
                .filter(verseList -> !verseList.isEmpty())
                .orElseThrow(ResourceNotFoundException::new);
        return new Resources<>(
                verses,
                entityLinks.linkForSingleResource(Song.class, songId).withRel("song"),
                linkTo(methodOn(this.getClass()).getAllVersesFromSong(songId)).withSelfRel()
        );
    }

    @GetMapping("/songs/{songId}/quotes/search/random")
    @ResponseBody
    public Resource<Quote> getRandomQuoteFromSong(@PathVariable("songId") Integer songId) {
        final Quote quote = Optional.ofNullable(quoteRepository.findRandomQuoteFromSong(songId))
                .orElseThrow(ResourceNotFoundException::new);
        return new Resource<>(
                quote,
                entityLinks.linkForSingleResource(Song.class, songId).withRel("song"),
                linkTo(methodOn(this.getClass()).getRandomQuoteFromSong(songId)).withSelfRel()
        );
    }

    @GetMapping("/songs/{songId}/verses/search/random")
    @ResponseBody
    public Resource<Verse> getRandomVerseFromSong(@PathVariable("songId") Integer songId) {
        final Verse verse = Optional.ofNullable(verseRepository.findRandomVerseFromSong(songId))
                .orElseThrow(ResourceNotFoundException::new);
        return new Resource<>(
                verse,
                entityLinks.linkForSingleResource(Song.class, songId).withRel("song"),
                linkTo(methodOn(this.getClass()).getRandomVerseFromSong(songId)).withSelfRel()
        );
    }
}

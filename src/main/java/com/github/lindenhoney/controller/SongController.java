package com.github.lindenhoney.controller;

import com.github.lindenhoney.domain.Quote;
import com.github.lindenhoney.domain.Song;
import com.github.lindenhoney.domain.Verse;
import com.github.lindenhoney.repository.QuoteRepository;
import com.github.lindenhoney.repository.VerseRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.hateoas.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
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
                linkTo(methodOn(SongController.class).getQuotesSearchResource(songId)).withRel("search"),
                linkTo(methodOn(getClass()).getAllQuotesFromSong(songId)).withSelfRel()
        );
    }

    @GetMapping("/songs/{songId}/quotes/search")
    @ResponseBody
    public ResourceSupport getQuotesSearchResource(@PathVariable("songId") Integer songId) {
        final ResourceSupport resource = new ResourceSupport();
        resource.add(
                linkTo(methodOn(this.getClass()).getRandomQuoteFromSong(songId)).withRel("random"),
                linkTo(methodOn(this.getClass()).findQuotesByPhraseFromSong(songId, null)).withRel("by-phrase"),
                linkTo(methodOn(this.getClass()).getQuotesSearchResource(songId)).withSelfRel()
        );
        return resource;
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

    @GetMapping("/songs/{songId}/quotes/search/by-phrase")
    @ResponseBody
    public Resources<Quote> findQuotesByPhraseFromSong(@PathVariable("songId") Integer songId, @RequestParam("phrase") String phrase) {
        final List<Quote> quotes = Optional.ofNullable(quoteRepository.findQuotesByPhraseFromSong(songId, phrase))
                .orElseThrow(ResourceNotFoundException::new);
        return new Resources<>(
                quotes,
                entityLinks.linkForSingleResource(Song.class, songId).withRel("song"),
                linkTo(methodOn(this.getClass()).findQuotesByPhraseFromSong(songId, phrase)).withSelfRel()
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
                linkTo(methodOn(SongController.class).getVersesSearchResource(songId)).withRel("search"),
                linkTo(methodOn(this.getClass()).getAllVersesFromSong(songId)).withSelfRel()
        );
    }

    @GetMapping("/songs/{songId}/verses/search")
    @ResponseBody
    public ResourceSupport getVersesSearchResource(@PathVariable("songId") Integer songId) {
        final ResourceSupport resource = new ResourceSupport();
        resource.add(
                linkTo(methodOn(this.getClass()).getRandomVerseFromSong(songId)).withRel("random"),
                linkTo(methodOn(this.getClass()).getVersesSearchResource(songId)).withSelfRel()
        );
        return resource;
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

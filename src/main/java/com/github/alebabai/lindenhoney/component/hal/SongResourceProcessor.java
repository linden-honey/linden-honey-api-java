package com.github.alebabai.lindenhoney.component.hal;

import com.github.alebabai.lindenhoney.controller.SongController;
import com.github.alebabai.lindenhoney.domain.Song;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class SongResourceProcessor implements ResourceProcessor<Resource<Song>> {

    @Override
    public Resource<Song> process(Resource<Song> resource) {
        final Integer id = resource.getContent().getId();
        resource.add(linkTo(methodOn(SongController.class).getRandomQuoteFromSong(id)).withRel("randomQuote"));
        resource.add(linkTo(methodOn(SongController.class).getRandomVerseFromSong(id)).withRel("randomVerse"));
        return resource;
    }
}

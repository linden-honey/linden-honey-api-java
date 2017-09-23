package com.github.lindenhoney.component.hal;

import com.github.lindenhoney.controller.SongController;
import com.github.lindenhoney.domain.Song;
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
        resource.add(linkTo(methodOn(SongController.class).getAllQuotesFromSong(id)).withRel("quotes"));
        resource.add(linkTo(methodOn(SongController.class).getAllVersesFromSong(id)).withRel("verses"));
        return resource;
    }
}

package com.github.lindenhoney.domain.projection;

import com.github.lindenhoney.domain.Song;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "min", types = {Song.class})
public interface SongMin {
    String getTitle();

    String getAuthor();

    String getAlbum();
}

package com.github.alebabai.lindenhoney.repository;

import com.github.alebabai.lindenhoney.domain.Song;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

import static com.github.alebabai.lindenhoney.util.EntityUtils.generateSong;
import static com.github.alebabai.lindenhoney.util.TestUtils.MAX_ENTITIES_COUNT;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isIn;
import static org.hamcrest.Matchers.notNullValue;

public class SongRepositoryTest extends AbstractRepositoryTest<Song, Integer, SongRepository> {
    @Override
    protected Song generateEntity() {
        return generateSong();
    }

    @Autowired
    private SongRepository songRepository;

    @Test
    public void findRandomSongTest() {
        final Collection<Song> songs = (Collection<Song>) songRepository.save(generateEntities(MAX_ENTITIES_COUNT));
        final Song randomSong = songRepository.findRandomSong();

        assertThat(randomSong, notNullValue());
        assertThat(randomSong, isIn(songs));
    }
}

package com.github.lindenhoney.repository;

import com.github.lindenhoney.domain.Song;
import com.github.lindenhoney.domain.Verse;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.util.Collection;
import java.util.Collections;

import static com.github.lindenhoney.util.EntityUtils.generateSong;
import static com.github.lindenhoney.util.EntityUtils.generateVerse;
import static com.github.lindenhoney.util.TestUtils.MAX_ENTITIES_COUNT;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

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

    @Test
    public void updateSongTest() {
        Song song = repository.save(generateEntity());

        song.setTitle("new title");
        song.setAlbum("new album");
        song.setAuthor("new author");

        final Verse verse1 = generateVerse();
        final Verse verse2 = generateVerse();
        song.setVerses(asList(verse1, verse2));

        song = repository.save(song);

        assertThat(song, samePropertyValuesAs(repository.findOne(song.getId())));
    }

    @Test
    public void deleteVersesTest() {
        Song song = repository.save(generateEntity());
        song.setVerses(Collections.emptyList());
        song = repository.save(song);

        assertThat(repository.findOne(song.getId()).getVerses(), emptyCollectionOf(Verse.class));
    }

    @Test
    public void findSongsByTitleContainingIgnoreCaseTest() {
        Song song = repository.save(generateEntity().setTitle("Some big title"));

        assertThat(repository.findSongsByTitleContainingIgnoreCase("some", new PageRequest(0, 1)).getContent(), contains(song));
    }

}

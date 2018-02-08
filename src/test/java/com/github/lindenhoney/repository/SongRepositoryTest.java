package com.github.lindenhoney.repository;

import com.github.lindenhoney.domain.Quote;
import com.github.lindenhoney.domain.Song;
import com.github.lindenhoney.domain.Verse;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

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
        Iterable<Song> songs = (Iterable<Song>) songRepository.saveAll(generateEntities(MAX_ENTITIES_COUNT));
        Song randomSong = songRepository.findRandomSong();

        assertThat(randomSong, notNullValue());
        assertThat(songs, hasItem(randomSong));
    }

    @Test
    public void updateSongTest() {
        Song song = repository.save(generateEntity());

        song.setTitle("new title");
        song.setAlbum("new album");
        song.setAuthor("new author");

        Verse verse1 = generateVerse();
        Verse verse2 = generateVerse();
        song.setVerses(asList(verse1, verse2));

        song = repository.save(song);

        assertThat(song, samePropertyValuesAs(repository.findById(song.getId())));
    }

    @Test
    public void deleteVersesTest() {
        Song song = repository.save(generateEntity());
        song.setVerses(Collections.emptyList());
        song = repository.save(song);

        assertThat(repository.findById(song.getId()).map(Song::getVerses).orElse(null), emptyCollectionOf(Verse.class));
    }

    @Test
    public void findSongsByTitleContainingIgnoreCaseTest() {
        Song song = repository.save(generateEntity().setTitle("Some big title"));

        assertThat(repository.findSongsByTitleContainingIgnoreCase("some", PageRequest.of(0, 1)).getContent(), contains(song));
    }

    @Test
    public void findDistinctSongsByVersesQuotesPhraseContainingIgnoreCaseTest() {
        Song song = repository.save(generateEntity());
        String phrase = song.getVerses()
                .stream()
                .findAny()
                .flatMap(verse -> verse.getQuotes()
                        .stream()
                        .findAny()
                        .map(Quote::getPhrase))
                .orElseThrow(() -> new IllegalStateException("Couldn't get phrase"));

        assertThat(repository.findDistinctSongsByVersesQuotesPhraseContainingIgnoreCase(phrase, PageRequest.of(0, 1)).getContent(), contains(song));
    }

}

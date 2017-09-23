package com.github.lindenhoney.repository;

import com.github.lindenhoney.domain.Song;
import com.github.lindenhoney.domain.Verse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.github.lindenhoney.util.EntityUtils.generateSong;
import static com.github.lindenhoney.util.EntityUtils.generateVerse;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.isIn;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(SpringRunner.class)
@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class VerseRepositoryTest {

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private VerseRepository verseRepository;

    @Test
    public void findRandomVerseTest() {
        final Song song = songRepository.save(generateSong());
        final Verse randomVerse = verseRepository.findRandomVerse();

        assertThat(randomVerse, notNullValue());
        assertThat(randomVerse, isIn(song.getVerses()));
    }

    @Test
    public void findRandomVerseFromSongTest() {
        final Song song = songRepository.save(generateSong());
        final Verse randomVerse = verseRepository.findRandomVerseFromSong(song.getId());

        assertThat(randomVerse, notNullValue());
        assertThat(randomVerse, isIn(song.getVerses()));
    }

    @Test
    public void findAllVersesFromSong() {
        final Verse verse1 = generateVerse();
        final Verse verse2 = generateVerse();
        final Song song = songRepository.save(generateSong()
                .setVerses(asList(verse1, verse2)));

        final List<Verse> verses = verseRepository.findAllVersesFromSong(song.getId());

        assertThat(verses, contains(verse1, verse2));
    }
}

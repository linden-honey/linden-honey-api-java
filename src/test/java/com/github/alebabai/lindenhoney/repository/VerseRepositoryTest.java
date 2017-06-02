package com.github.alebabai.lindenhoney.repository;

import com.github.alebabai.lindenhoney.domain.Song;
import com.github.alebabai.lindenhoney.domain.Verse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.github.alebabai.lindenhoney.util.EntityUtils.generateSong;
import static org.hamcrest.MatcherAssert.assertThat;
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
}

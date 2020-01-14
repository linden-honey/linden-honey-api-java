package com.github.lindenhoney.repository;

import com.github.lindenhoney.AbstractIntegrationTest;
import com.github.lindenhoney.entity.Quote;
import com.github.lindenhoney.entity.Song;
import com.github.lindenhoney.entity.Verse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SongRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private SongRepository repository;

    @Test
    void saveSongTest() {
        final Song song = new Song()
                .setTitle("Song")
                .setVerses(List.of(
                        new Verse()
                                .setQuotes(
                                        List.of(
                                                new Quote().setPhrase("1"),
                                                new Quote().setPhrase("2"),
                                                new Quote().setPhrase("3")
                                        )
                                )
                ));
        repository.save(song);
    }
}

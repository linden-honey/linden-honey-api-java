package com.github.lindenhoney.repository;

import com.github.lindenhoney.AbstractIntegrationTest;
import com.github.lindenhoney.entity.QuoteEntity;
import com.github.lindenhoney.entity.SongEntity;
import com.github.lindenhoney.entity.VerseEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SongRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private SongRepository repository;

    @Test
    void saveSongTest() {
        final SongEntity song = new SongEntity()
                .setTitle("Song")
                .setVerses(List.of(
                        new VerseEntity()
                                .setQuotes(
                                        List.of(
                                                new QuoteEntity().setPhrase("1"),
                                                new QuoteEntity().setPhrase("2"),
                                                new QuoteEntity().setPhrase("3")
                                        )
                                )
                ));
        repository.save(song);
    }
}

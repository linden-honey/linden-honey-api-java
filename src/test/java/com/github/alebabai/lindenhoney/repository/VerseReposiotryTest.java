package com.github.alebabai.lindenhoney.repository;

import com.github.alebabai.lindenhoney.domain.Song;
import com.github.alebabai.lindenhoney.domain.Verse;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;

import static com.github.alebabai.lindenhoney.util.TestUtils.MAX_STRING_LENGTH;
import static com.github.alebabai.lindenhoney.util.TestUtils.getRandomString;

public class VerseReposiotryTest extends AbstractRepositoryTest<Verse, Long, VerseRepository> {

    @Autowired
    private SongRepository songRepository;

    @Override
    protected Verse generateEntity() {
        final Song song = songRepository.save(new Song(getRandomString(MAX_STRING_LENGTH), getRandomString(MAX_STRING_LENGTH), Collections.emptyList()));
        return new Verse(Collections.emptyList(), song);
    }
}

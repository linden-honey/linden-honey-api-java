package com.github.alebabai.lindenhoney.repository;

import com.github.alebabai.lindenhoney.domain.Song;
import com.github.alebabai.lindenhoney.domain.Verse;

import javax.transaction.Transactional;
import java.util.Collections;

import static com.github.alebabai.lindenhoney.util.TestUtils.MAX_STRING_LENGTH;
import static com.github.alebabai.lindenhoney.util.TestUtils.getRandomString;

@Transactional
public class VerseRepositoryTest extends AbstractRepositoryTest<Verse, Integer, VerseRepository> {

    @Override
    protected Verse generateEntity() {
        final Song song = new Song(getRandomString(MAX_STRING_LENGTH), getRandomString(MAX_STRING_LENGTH), getRandomString(MAX_STRING_LENGTH), Collections.emptyList());
        return new Verse(Collections.emptyList(), song);
    }
}

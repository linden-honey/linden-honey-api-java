package com.github.alebabai.lindenhoney.repository;

import com.github.alebabai.lindenhoney.domain.Song;

import javax.transaction.Transactional;
import java.util.Collections;

import static com.github.alebabai.lindenhoney.util.TestUtils.MAX_STRING_LENGTH;
import static com.github.alebabai.lindenhoney.util.TestUtils.getRandomString;

@Transactional
public class SongRepositoryTest extends AbstractRepositoryTest<Song, Integer, SongRepository> {
    @Override
    protected Song generateEntity() {
        return new Song(getRandomString(MAX_STRING_LENGTH), getRandomString(MAX_STRING_LENGTH), getRandomString(MAX_STRING_LENGTH), Collections.emptyList());
    }
}

package com.github.alebabai.lindenhoney.repository;

import com.github.alebabai.lindenhoney.domain.Song;

import java.util.Collections;

import static com.github.alebabai.lindenhoney.util.TestUtils.MAX_STRING_LENGTH;
import static com.github.alebabai.lindenhoney.util.TestUtils.getRandomString;

public class SongReposiotryTest extends AbstractRepositoryTest<Song, Long, SongRepository> {
    @Override
    protected Song generateEntity() {
        return new Song(getRandomString(MAX_STRING_LENGTH), getRandomString(MAX_STRING_LENGTH), Collections.emptyList());
    }
}

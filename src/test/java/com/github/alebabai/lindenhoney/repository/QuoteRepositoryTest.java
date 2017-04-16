package com.github.alebabai.lindenhoney.repository;

import com.github.alebabai.lindenhoney.domain.Quote;
import com.github.alebabai.lindenhoney.domain.Song;
import com.github.alebabai.lindenhoney.domain.Verse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

import static com.github.alebabai.lindenhoney.util.TestUtils.MAX_STRING_LENGTH;
import static com.github.alebabai.lindenhoney.util.TestUtils.getRandomString;

@Transactional
public class QuoteRepositoryTest extends AbstractRepositoryTest<Quote, Integer, QuoteRepository> {

    @Autowired
    private VerseRepository verseRepository;

    @Autowired
    private SongRepository songRepository;

    @Override
    protected Quote generateEntity() {
        final Song song = songRepository.save(new Song(getRandomString(MAX_STRING_LENGTH), getRandomString(MAX_STRING_LENGTH), Collections.emptyList()));
        final Verse verse = verseRepository.save(new Verse(Collections.emptyList(), song));
        return new Quote(getRandomString(MAX_STRING_LENGTH), verse);
    }
}

package com.github.alebabai.lindenhoney.repository;

import com.github.alebabai.lindenhoney.domain.Quote;
import com.github.alebabai.lindenhoney.domain.Song;
import com.github.alebabai.lindenhoney.domain.Verse;

import javax.transaction.Transactional;

import static com.github.alebabai.lindenhoney.util.TestUtils.MAX_STRING_LENGTH;
import static com.github.alebabai.lindenhoney.util.TestUtils.getRandomString;
import static java.util.Arrays.asList;

@Transactional
public class SongRepositoryTest extends AbstractRepositoryTest<Song, Integer, SongRepository> {
    @Override
    protected Song generateEntity() {
        final Quote quote1 = new Quote("Some phrase");
        final Quote quote2 = new Quote("Some phrase");
        final Quote quote3 = new Quote("Some phrase");
        final Quote quote4 = new Quote("Some phrase");

        final Verse verse1 = new Verse(asList(quote1, quote2));
        final Verse verse2 = new Verse(asList(quote3, quote4));

        return new Song(getRandomString(
                MAX_STRING_LENGTH),
                getRandomString(MAX_STRING_LENGTH),
                getRandomString(MAX_STRING_LENGTH),
                asList(verse1, verse2)
        );
    }
}

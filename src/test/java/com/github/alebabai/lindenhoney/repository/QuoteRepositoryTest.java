package com.github.alebabai.lindenhoney.repository;

import com.github.alebabai.lindenhoney.domain.Quote;
import com.github.alebabai.lindenhoney.domain.Song;
import com.github.alebabai.lindenhoney.domain.Verse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;

import static com.github.alebabai.lindenhoney.util.EntityUtils.generateQuote;
import static com.github.alebabai.lindenhoney.util.EntityUtils.generateSong;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class QuoteRepositoryTest {

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private QuoteRepository quoteRepository;

    @Test
    public void findRandomQuoteTest() {
        final Song song = songRepository.save(generateSong());
        final Quote randomQuote = quoteRepository.findRandomQuote();
        final List<Quote> quotes = song.getVerses()
                .stream()
                .flatMap(verse -> verse.getQuotes().stream())
                .collect(Collectors.toList());

        assertThat(randomQuote, notNullValue());
        assertThat(randomQuote, isIn(quotes));
    }

    @Test
    public void findAllQuotesFromSong() {
        final Quote quote1 = generateQuote();
        final Quote quote2 = generateQuote();
        final Verse verse = new Verse(asList(quote1, quote2));
        final Song song = songRepository.save(generateSong()
                .setVerses(singletonList(verse)));

        final List<Quote> quotes = quoteRepository.findAllQuotesFromSong(song.getId());
        assertThat(quotes, contains(quote1, quote2));
    }
}

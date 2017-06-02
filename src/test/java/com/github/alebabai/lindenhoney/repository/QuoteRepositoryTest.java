package com.github.alebabai.lindenhoney.repository;

import com.github.alebabai.lindenhoney.domain.Quote;
import com.github.alebabai.lindenhoney.domain.Song;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;

import static com.github.alebabai.lindenhoney.util.EntityUtils.generateSong;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isIn;
import static org.hamcrest.Matchers.notNullValue;

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
}

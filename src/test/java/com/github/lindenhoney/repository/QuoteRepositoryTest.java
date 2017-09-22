package com.github.lindenhoney.repository;

import com.github.lindenhoney.domain.Quote;
import com.github.lindenhoney.domain.Song;
import com.github.lindenhoney.domain.Verse;
import com.github.lindenhoney.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;

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
        final Song song = songRepository.save(EntityUtils.generateSong());
        final Quote randomQuote = quoteRepository.findRandomQuote();
        final List<Quote> quotes = song.getVerses()
                .stream()
                .flatMap(verse -> verse.getQuotes().stream())
                .collect(Collectors.toList());

        assertThat(randomQuote, notNullValue());
        assertThat(randomQuote, isIn(quotes));
    }

    @Test
    public void findRandomQuoteFromSongTest() {
        final Song song = songRepository.save(EntityUtils.generateSong());
        final Quote randomQuote = quoteRepository.findRandomQuoteFromSong(song.getId());
        final List<Quote> quotes = song.getVerses()
                .stream()
                .flatMap(verse -> verse.getQuotes().stream())
                .collect(Collectors.toList());

        assertThat(randomQuote, notNullValue());
        assertThat(randomQuote, isIn(quotes));
    }

    @Test
    public void findQuotesByPhraseContainingIgnoreCaseTest() {
        final Quote quote1 = new Quote("Some QUOTE! Next Phrase");
        final Quote quote2 = new Quote("Another quote");
        final Verse verse = new Verse(asList(quote1, quote2));
        songRepository.save(EntityUtils.generateSong()
                .setVerses(singletonList(verse)));

        final Page<Quote> page = quoteRepository.findQuotesByPhraseContainingIgnoreCase("some quote", new PageRequest(0, 10));
        assertThat(page.getContent(), contains(quote1));
    }

    @Test
    public void findAllQuotesFromSong() {
        final Quote quote1 = EntityUtils.generateQuote();
        final Quote quote2 = EntityUtils.generateQuote();
        final Verse verse = new Verse(asList(quote1, quote2));
        final Song song = songRepository.save(EntityUtils.generateSong()
                .setVerses(singletonList(verse)));

        final List<Quote> quotes = quoteRepository.findAllQuotesFromSong(song.getId());
        assertThat(quotes, contains(quote1, quote2));
    }
}

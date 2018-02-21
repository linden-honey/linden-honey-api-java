package com.github.lindenhoney.util;

import com.github.lindenhoney.domain.Quote;
import com.github.lindenhoney.domain.Verse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class ParserTest {

    @Test
    @Tag("quote")
    @DisplayName("Should return quote object with a phrase string")
    public void parseQuoteTest() {
        final String html = "Some phrase";
        final Quote quote = Parser.parseQuote(html);
        assertThat(quote)
                .isNotNull()
                .extracting(Quote::getPhrase)
                .isEqualTo("Some phrase");
    }

    @Test
    @Tag("quote")
    @DisplayName("Should replace all trailing spaces in a phrase")
    public void parseQuoteWithTrailingSpacesTest() {
        final String html = "    Some text    with    trailing spaces  ";
        final Quote quote = Parser.parseQuote(html);
        assertThat(quote)
                .isNotNull()
                .extracting(Quote::getPhrase)
                .isEqualTo("Some text with trailing spaces");
    }

    @Test
    @Tag("quote")
    @DisplayName("Should convert all html formatting tags to regular text")
    public void parseQuoteWithHtmlFormattingTagsTest() {
        final String html = "<strong>Some</strong> text<br\\> with html<br\\> <i>formatting</i> <b>tags</b>";
        final Quote quote = Parser.parseQuote(html);
        assertThat(quote)
                .isNotNull()
                .extracting(Quote::getPhrase)
                .isEqualTo("Some text with html formatting tags");
    }

    @Test
    @Tag("verse")
    @DisplayName("Should return verse object with a quotes array")
    public void parseVerseTest() {
        final String html = "Some phrase";
        final Verse verse = Parser.parseVerse(html);
        assertThat(verse).isNotNull();
        assertThat(verse.getQuotes())
                .extracting(Quote::getPhrase)
                .containsOnly("Some phrase");
    }

    @Test
    @Tag("verse")
    @DisplayName("Should parse all phrases into a quotes array")
    public void parseVerseWithSeveralQuotesTest() {
        final String html = ""
                + "Some phrase 1"
                + "<br>"
                + "Some phrase 2"
                + "<br>"
                + "Some phrase 3";
        final Verse verse = Parser.parseVerse(html);
        assertThat(verse).isNotNull();
        assertThat(verse.getQuotes())
                .extracting(Quote::getPhrase)
                .containsOnly(
                        "Some phrase 1",
                        "Some phrase 2",
                        "Some phrase 3"
                );
    }

    @Test
    @Tag("lyrics")
    @DisplayName("Should return array with verses objects")
    public void parseLyricsTest() {
        final String html = ""
                + "Some phrase 1"
                + "<br><br><br>"
                + "Some phrase 2"
                + "<br>"
                + "Some phrase 3"
                + "<br> <br>"
                + "Some phrase 4";
        final List<Verse> verses = Parser.parseLyrics(html);
        assertThat(verses).isNotEmpty();
        assertThat(verses.stream()
                .flatMap(verse -> verse.getQuotes()
                        .stream()
                        .map(Quote::getPhrase))
                .collect(Collectors.toList()))
                .containsOnly(
                        "Some phrase 1",
                        "Some phrase 2",
                        "Some phrase 3",
                        "Some phrase 4"
                );
    }
}

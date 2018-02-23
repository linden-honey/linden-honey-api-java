package com.github.lindenhoney.util;

import com.github.lindenhoney.domain.Quote;
import com.github.lindenhoney.domain.SongPreview;
import com.github.lindenhoney.domain.Verse;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

class GrobParserTest {

    @Test
    @Tag("quote")
    @DisplayName("Should return quote object with a phrase string")
    void parseQuoteTest() {
        final String html = "Some phrase";
        final Quote quote = GrobParser.parseQuote(html).block();
        assertThat(quote)
                .extracting(Quote::getPhrase)
                .containsExactly("Some phrase");
    }

    @Test
    @Tag("quote")
    @DisplayName("Should replace all trailing spaces in a phrase")
    void parseQuoteWithTrailingSpacesTest() {
        final String html = "    Some text    with    trailing spaces  ";
        final Quote quote = GrobParser.parseQuote(html).block();
        assertThat(quote)
                .isNotNull()
                .extracting(Quote::getPhrase)
                .containsExactly("Some text with trailing spaces");
    }

    @Test
    @Tag("quote")
    @DisplayName("Should convert all html formatting tags to regular text")
    void parseQuoteWithHtmlFormattingTagsTest() {
        final String html = "<strong>Some</strong> text<br> with html<br> <i>formatting</i> <b>tags</b>";
        final Quote quote = GrobParser.parseQuote(html).block();
        assertThat(quote)
                .isNotNull()
                .extracting(Quote::getPhrase)
                .containsExactly("Some text with html formatting tags");
    }

    @Test
    @Tag("verse")
    @DisplayName("Should return verse object with a quotes array")
    void parseVerseTest() {
        final String html = "Some phrase";
        final Verse verse = GrobParser.parseVerse(html).block();
        assertThat(verse).isNotNull();
        assertThat(verse.getQuotes())
                .extracting(Quote::getPhrase)
                .containsOnly("Some phrase");
    }

    @Test
    @Tag("verse")
    @DisplayName("Should parse all phrases into a quotes array")
    void parseVerseWithSeveralQuotesTest() {
        final String html = ""
                + "Some phrase 1"
                + "<br>"
                + "Some phrase 2"
                + "<br>"
                + "Some phrase 3";
        final Verse verse = GrobParser.parseVerse(html).block();
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
    void parseLyricsTest() {
        final String html = ""
                + "Some phrase 1"
                + "<br><br><br>"
                + "Some phrase 2"
                + "<br>"
                + "Some phrase 3"
                + "<br> <br>"
                + "Some phrase 4";
        final List<Verse> verses = GrobParser.parseLyrics(html).collectList().block();
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

    @Test
    @Tag("preview")
    @DisplayName("Should return array with preview objects")
    void parsePreviews() {
        final String html = ""
                + "<ul id=\"abc_list\">"
                + "<li><a href=\"/texts/1056899068.html\">Всё идёт по плану</a></li>"
                + "<li><a href=\"\">Unknown</a></li>"
                + "<li><a href=\"/texts/1056901056.html\">Всё как у людей</a></li>"
                + "</ul>";
        final List<SongPreview> previews = GrobParser.parsePreviews(html).collectList().block();
        assertThat(previews)
                .isNotEmpty()
                .extracting(SongPreview::getId, SongPreview::getTitle)
                .containsExactly(
                        tuple(1056899068L, "Всё идёт по плану"),
                        tuple(1056901056L, "Всё как у людей")
                );
    }

    @Test
    @Tag("preview")
    @DisplayName("Should return empty array")
    void parseEmptyPreviews() {
        assertThat(GrobParser.parsePreviews(StringUtils.EMPTY).collectList().block())
                .isNotNull()
                .isEmpty();
    }
}

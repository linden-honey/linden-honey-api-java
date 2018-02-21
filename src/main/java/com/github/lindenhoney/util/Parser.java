package com.github.lindenhoney.util;

import com.github.lindenhoney.domain.Quote;
import com.github.lindenhoney.domain.Song;
import com.github.lindenhoney.domain.Verse;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@UtilityClass
public class Parser {

    //TODO handle incorrect input

    public static Quote parseQuote(String html) {
        final String phrase = Jsoup.parse(html).text().replaceAll("\\s+", StringUtils.SPACE);
        return new Quote(phrase);
    }

    public static Verse parseVerse(String html) {
        final List<Quote> quotes = Arrays.stream(html.split("<br>"))
                .map(Quote::new)
                .collect(Collectors.toList());
        return new Verse(quotes);
    }

    protected static List<Verse> parseLyrics(String html) {
        return Optional.ofNullable(html)
                .map(it -> Arrays.stream(it.split("(?:<br\\>\\s*){2,}"))
                        .map(Parser::parseVerse)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    public static Song parseSong(String html) {
        final Document document = Jsoup.parse(html);
        final String title = Optional.ofNullable(document.selectFirst("h2"))
                .map(Element::text)
                .orElse(null);
        final String author = Optional.ofNullable(document.selectFirst("p:has(strong:contains(Автор))"))
                .map(el -> el.text().split(": ")[1])//TODO refactor split to some search query
                .orElse(null);
        final String album = Optional.ofNullable(document.selectFirst("p:has(strong:contains(Альбом))"))
                .map(el -> el.text().split(": ")[1])//TODO refactor split to some search query
                .orElse(null);
        final String lyricsHtml = document.selectFirst("p:last-of-type").html();
        final List<Verse> verses = parseLyrics(lyricsHtml);
        return new Song(title, author, album, verses);
    }
}

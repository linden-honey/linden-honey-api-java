package com.github.lindenhoney.util;

import com.github.lindenhoney.domain.Quote;
import com.github.lindenhoney.domain.Song;
import com.github.lindenhoney.domain.SongPreview;
import com.github.lindenhoney.domain.Verse;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.omg.CORBA.UNKNOWN;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.executable.ValidateOnExecution;
import java.util.*;
import java.util.stream.Collectors;

@UtilityClass
public class GrobParser {

    //TODO Global - handle incorrect input (assert or return Optional)
    //TODO Global - add validation

    protected static Quote parseQuote(String html) {
        final String phrase = Jsoup.parseBodyFragment(html).text().replaceAll("\\s+", StringUtils.SPACE);
        return new Quote(phrase);
    }

    protected static Verse parseVerse(String html) {
        final List<Quote> quotes = Arrays.stream(html.split("<br>"))
                .map(Quote::new)
                .collect(Collectors.toList());
        return new Verse(quotes);
    }

    protected static List<Verse> parseLyrics(String html) {
        return Optional.ofNullable(html)
                .map(it -> Arrays.stream(it.split("(?:<br\\>\\s*){2,}"))
                        .map(GrobParser::parseVerse)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    @ValidateOnExecution
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

    public static List<SongPreview> parsePreviews(String html) {
        return Jsoup.parse(html)
                .select("#abc_list a")
                .stream()
                .map(link -> {
                    final Long id = Optional.ofNullable(link.attr("href"))
                            .filter(StringUtils::isNotBlank)
                            .map(path -> path.substring(path.lastIndexOf('/') + 1, path.indexOf('.')))//TODO change to regexp
                            .map(Long::parseLong)
                            .orElse(null);//TODO validation
                    final String title = link.text();
                    return new SongPreview(id, title);
                })
                .filter(preview -> Objects.nonNull(preview.getId()))//TODO proper validation
                .collect(Collectors.toList());
    }
}

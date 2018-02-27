package com.github.lindenhoney.util;

import com.github.lindenhoney.domain.Quote;
import com.github.lindenhoney.domain.Song;
import com.github.lindenhoney.domain.SongPreview;
import com.github.lindenhoney.domain.Verse;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class GrobParser {

    //TODO Global - add validation

    protected static Optional<Quote> parseQuote(String html) {
        return Optional.ofNullable(html)
                .map(Jsoup::parseBodyFragment)
                .map(Element::text)
                .map(text -> text.replaceAll("\\s+", StringUtils.SPACE))
                .map(Quote::new);
    }

    protected static Optional<Verse> parseVerse(String html) {
        return Optional.ofNullable(html)
                .map(it -> Arrays.stream(html.split("<br>"))
                        .map(GrobParser::parseQuote)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toList()))
                .map(Verse::new);
    }

    protected static Stream<Verse> parseLyrics(String html) {
        return Optional.ofNullable(html)
                .map(it -> Arrays.stream(it.split("(?:<br\\>\\s*){2,}"))
                        .map(GrobParser::parseVerse)
                        .filter(Optional::isPresent)
                        .map(Optional::get))
                .orElseGet(Stream::empty);
    }

    public static Optional<Song> parseSong(String html) {
        return Optional.ofNullable(html)
                .map(Jsoup::parse)
                .map(document -> {
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
                    final List<Verse> verses = parseLyrics(lyricsHtml).collect(Collectors.toList());
                    return new Song(title, author, album, verses);
                });
    }

    public static Stream<SongPreview> parsePreviews(String html) {
        return Optional.ofNullable(html)
                .map(Jsoup::parse)
                .map(document -> document.select("#abc_list a")
                        .stream()
                        .map(link -> {
                            final Long id = Optional.ofNullable(link.attr("href"))
                                    .filter(StringUtils::isNotBlank)
                                    .map(path -> path.substring(path.lastIndexOf('/') + 1, path.indexOf('.')))//TODO change to regexp
                                    .map(Long::parseLong)
                                    .orElse(null);
                            final String title = link.text();
                            return new SongPreview(id, title);
                        }))
                .orElseGet(Stream::empty);
    }
}

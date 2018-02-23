package com.github.lindenhoney.util;

import com.github.lindenhoney.domain.Quote;
import com.github.lindenhoney.domain.Song;
import com.github.lindenhoney.domain.SongPreview;
import com.github.lindenhoney.domain.Verse;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@UtilityClass
public class GrobParser {

    //TODO Global - handle incorrect input (assert or return Optional)
    //TODO Global - add validation

    protected static Mono<Quote> parseQuote(String html) {
        return Mono.justOrEmpty(html)
                .map(Jsoup::parseBodyFragment)
                .map(Element::text)
                .map(text -> text.replaceAll("\\s+", StringUtils.SPACE))
                .map(Quote::new);
    }

    protected static Mono<Verse> parseVerse(String html) {
        return Mono.justOrEmpty(html)
                .flatMapIterable(it -> Arrays.asList(html.split("<br>")))
                .map(Quote::new)
                .collectList()
                .map(Verse::new);
    }

    protected static Flux<Verse> parseLyrics(String html) {
        return Mono.justOrEmpty(html)
                .flatMapIterable(it -> Arrays.asList(it.split("(?:<br\\>\\s*){2,}")))
                .flatMap(GrobParser::parseVerse);
    }

    public static Mono<Song> parseSong(String html) {
        return Mono.justOrEmpty(html)
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
                    final List<Verse> verses = parseLyrics(lyricsHtml).collectList().block();
                    return new Song(title, author, album, verses);
                });
    }

    public static Flux<SongPreview> parsePreviews(String html) {
        return Mono.justOrEmpty(html)
                .map(Jsoup::parse)
                .flatMapIterable(document -> document.select("#abc_list a"))
                .map(link -> {
                    final Long id = Optional.ofNullable(link.attr("href"))
                            .filter(StringUtils::isNotBlank)
                            .map(path -> path.substring(path.lastIndexOf('/') + 1, path.indexOf('.')))//TODO change to regexp
                            .map(Long::parseLong)
                            .orElse(null);//TODO validation
                    final String title = link.text();
                    return new SongPreview(id, title);
                })
                .filter(preview -> Objects.nonNull(preview.getId()));//TODO proper validation
    }
}

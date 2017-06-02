package com.github.alebabai.lindenhoney.util;


import com.github.alebabai.lindenhoney.domain.Quote;
import com.github.alebabai.lindenhoney.domain.Song;
import com.github.alebabai.lindenhoney.domain.Verse;

import java.util.List;
import java.util.stream.IntStream;

import static com.github.alebabai.lindenhoney.util.TestUtils.*;
import static java.util.stream.Collectors.toList;

public abstract class EntityUtils {

    public static Quote generateQuote() {
        return new Quote(getRandomString(MAX_STRING_LENGTH));
    }

    public static Verse generateVerse() {
        final List<Quote> quotes = IntStream.range(0, MAX_ENTITIES_COUNT)
                .boxed()
                .map(it -> generateQuote())
                .collect(toList());
        return new Verse(quotes);
    }

    public static Song generateSong() {
        final List<Verse> verses = IntStream.range(0, MAX_ENTITIES_COUNT)
                .boxed()
                .map(it -> generateVerse())
                .collect(toList());
        return new Song(
                getRandomString(MAX_STRING_LENGTH),
                getRandomString(MAX_STRING_LENGTH),
                getRandomString(MAX_STRING_LENGTH),
                verses
        );
    }
}

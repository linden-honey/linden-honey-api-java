package com.github.lindenhoney.util;


import com.github.lindenhoney.domain.Quote;
import com.github.lindenhoney.domain.Song;
import com.github.lindenhoney.domain.Verse;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public abstract class EntityUtils {

    public static Quote generateQuote() {
        return new Quote(TestUtils.getRandomString(TestUtils.MAX_STRING_LENGTH));
    }

    public static Verse generateVerse() {
        final List<Quote> quotes = IntStream.range(0, TestUtils.MAX_ENTITIES_COUNT)
                .boxed()
                .map(it -> generateQuote())
                .collect(toList());
        return new Verse(quotes);
    }

    public static Song generateSong() {
        final List<Verse> verses = IntStream.range(0, TestUtils.MAX_ENTITIES_COUNT)
                .boxed()
                .map(it -> generateVerse())
                .collect(toList());
        return new Song(
                TestUtils.getRandomString(TestUtils.MAX_STRING_LENGTH),
                TestUtils.getRandomString(TestUtils.MAX_STRING_LENGTH),
                TestUtils.getRandomString(TestUtils.MAX_STRING_LENGTH),
                verses
        );
    }
}

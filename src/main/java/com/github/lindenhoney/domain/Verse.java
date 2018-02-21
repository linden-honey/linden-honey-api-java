package com.github.lindenhoney.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(exclude = {"quotes"})
@ToString(exclude = {"quotes"})
public class Verse {

    private List<Quote> quotes = new ArrayList<>();

    public Verse(List<Quote> quotes) {
        this.quotes = quotes;
    }
}

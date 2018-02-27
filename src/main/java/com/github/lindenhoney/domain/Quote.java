package com.github.lindenhoney.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Quote {
    //TODO refine properties validation (for all cases, persistence and parser)
    //TODO move validation messages to bundle

    @NotNull(message = "Phrase is required!")
    private String phrase;

    public Quote(String phrase) {
        this.phrase = phrase;
    }
}

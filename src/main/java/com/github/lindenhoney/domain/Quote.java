package com.github.lindenhoney.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Quote {

    @NotNull(message = "Phrase is required!")
    private String phrase;

    public Quote(String phrase) {
        this.phrase = phrase;
    }
}

package com.github.lindenhoney.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Quote {
    //TODO refine properties validation (for all cases, persistence and parser)
    //TODO move validation messages to bundle

    @NotBlank
    private String phrase;
}

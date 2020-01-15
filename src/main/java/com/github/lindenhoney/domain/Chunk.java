package com.github.lindenhoney.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Chunk<T> {

    @NotEmpty
    private final List<T> data;

    @PositiveOrZero
    @Builder.Default
    private final int limit;

    @PositiveOrZero
    private final int offset;

    @PositiveOrZero
    private final int total;

    @NotBlank
    private final String sortBy;

    @NotBlank
    @Builder.Default
    private final String sortOrder;
}

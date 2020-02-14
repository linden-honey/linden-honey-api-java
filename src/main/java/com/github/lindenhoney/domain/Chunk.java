package com.github.lindenhoney.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import java.util.ArrayList;
import java.util.List;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Chunk<T> {

    public static final int DEFAULT_LIMIT = 20;
    public static final String DEFAULT_SORT_ORDER = "asc";

    @NotEmpty
    @Builder.Default
    private final List<T> data = new ArrayList<>();

    @PositiveOrZero
    @Builder.Default
    private final int limit = DEFAULT_LIMIT;

    @PositiveOrZero
    private final int offset;

    @PositiveOrZero
    private final int total;

    @NotBlank
    private final String sortBy;

    @NotBlank
    @Builder.Default
    private final String sortOrder = DEFAULT_SORT_ORDER;
}

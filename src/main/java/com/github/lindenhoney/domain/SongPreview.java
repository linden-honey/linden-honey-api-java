package com.github.lindenhoney.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SongPreview {

    @NotNull(message = "Id is required")
    private Long id;

    @NotNull(message = "Title is required!")
    private String title;
}

package com.github.lindenhoney.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(exclude = {"verses"})
@ToString(exclude = {"verses"})
@Document
public class Song {

    @Id
    private Integer id;

    @TextIndexed
    @NotNull(message = "Title is required!")
    private String title;

    private String author;

    private String album;

    private List<Verse> verses = new ArrayList<>();

    public Song(String title, String author, String album, List<Verse> verses) {
        this.title = title;
        this.author = author;
        this.album = album;
        this.verses = verses;
    }
}

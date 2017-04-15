package com.github.alebabai.lindenhoney.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(exclude = {"verses"})
@ToString(exclude = {"verses"})
@Entity
@Table(name = "song")
public class Song {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "author")
    private String author;

    @OneToMany(mappedBy = "song", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Verse> verses = new ArrayList<>();

    public Song setVerses(List<Verse> verses) {
        this.verses.clear();
        this.verses.addAll(verses);
        return this;
    }
}

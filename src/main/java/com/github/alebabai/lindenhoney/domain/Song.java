package com.github.alebabai.lindenhoney.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Accessors(fluent = true)
@EqualsAndHashCode(exclude = {"verses"})
@ToString(exclude = {"verses"})
@Entity
@Table(name = "song")
public class Song implements Persistable<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "song_id_seq")
    @SequenceGenerator(name = "song_id_seq", sequenceName = "song_id_seq", allocationSize = 1)
    private Integer id;

    @NotNull(message = "Title is required!")
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "author")
    private String author;

    @OneToMany(mappedBy = "song", cascade = CascadeType.ALL)
    private List<Verse> verses = new ArrayList<>();

    public Song(String title, List<Verse> verses) {
        this.title = title;
        this.verses = verses;
    }

    public Song(String title, String author, List<Verse> verses) {
        this.title = title;
        this.author = author;
        this.verses = verses;
    }

    @JsonIgnore
    @Override
    public Integer getId() {
        return id;
    }

    @JsonIgnore
    @Override
    public boolean isNew() {
        return id == null;
    }
}

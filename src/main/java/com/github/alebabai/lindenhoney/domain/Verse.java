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
@Accessors(chain = true)
@EqualsAndHashCode(exclude = {"quotes"})
@ToString(exclude = {"quotes"})
@Entity
@Table(name = "verse")
public class Verse implements Persistable<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "verse_id_seq")
    @SequenceGenerator(name = "verse_id_seq", sequenceName = "verse_id_seq", allocationSize = 1)
    private Integer id;

    @OneToMany(mappedBy = "verse", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Quote> quotes = new ArrayList<>();

    @NotNull(message = "Song is required!")
    @ManyToOne(optional = false, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "song_id", referencedColumnName = "id", nullable = false, updatable = false)
    private Song song;

    public Verse(List<Quote> quotes, Song song) {
        this.setQuotes(quotes);
        this.song = song;
    }

    public Verse setQuotes(List<Quote> quotes) {
        this.quotes.clear();
        this.quotes.addAll(quotes);
        return this;
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

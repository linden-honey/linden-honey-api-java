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
@EqualsAndHashCode(exclude = {"quotes"})
@ToString(exclude = {"quotes"})
@Entity
@Table(name = "verse")
public class Verse {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "verse", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Quote> quotes = new ArrayList<>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "song_id", referencedColumnName = "id", nullable = false, updatable = false)
    private Song song;

    public Verse setQuotes(List<Quote> quotes) {
        this.quotes.clear();
        this.quotes.addAll(quotes);
        return this;
    }
}

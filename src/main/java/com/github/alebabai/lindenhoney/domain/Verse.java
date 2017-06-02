package com.github.alebabai.lindenhoney.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
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

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "verse_id", referencedColumnName = "id", nullable = false, updatable = false)
    private List<Quote> quotes = new ArrayList<>();

    public Verse(List<Quote> quotes) {
        this.quotes = quotes;
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

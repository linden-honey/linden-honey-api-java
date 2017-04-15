package com.github.alebabai.lindenhoney.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "quote")
public class Quote implements Persistable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quote_id_seq")
    @SequenceGenerator(name = "quote_id_seq", sequenceName = "quote_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "phrase", nullable = false)
    private String phrase;

    @ManyToOne(optional = false)
    @JoinColumn(name = "song_id", referencedColumnName = "id", nullable = false, updatable = false)
    private Verse verse;

    public Quote(String phrase, Verse verse) {
        this.phrase = phrase;
        this.verse = verse;
    }

    @JsonIgnore
    @Override
    public Long getId() {
        return id;
    }

    @JsonIgnore
    @Override
    public boolean isNew() {
        return id == null;
    }
}

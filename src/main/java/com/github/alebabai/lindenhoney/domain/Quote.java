package com.github.alebabai.lindenhoney.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "quote")
public class Quote implements Persistable<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quote_id_seq")
    @SequenceGenerator(name = "quote_id_seq", sequenceName = "quote_id_seq", allocationSize = 1)
    private Integer id;

    @NotNull(message = "Phrase is required!")
    @Column(name = "phrase", nullable = false)
    private String phrase;

    @NotNull(message = "Verse is required!")
    @ManyToOne(optional = false, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "verse_id", referencedColumnName = "id", nullable = false, updatable = false)
    private Verse verse;

    public Quote(String phrase, Verse verse) {
        this.phrase = phrase;
        this.verse = verse;
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

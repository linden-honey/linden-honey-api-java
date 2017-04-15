package com.github.alebabai.lindenhoney.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "quote")
public class Quote {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "phrase", nullable = false)
    private String phrase;

    @ManyToOne(optional = false)
    @JoinColumn(name = "song_id", referencedColumnName = "id", nullable = false, updatable = false)
    private Verse verse;
}

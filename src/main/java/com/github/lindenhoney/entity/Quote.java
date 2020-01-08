package com.github.lindenhoney.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Table(name = "quote")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Quote {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quote_id_seq")
    @SequenceGenerator(name = "quote_id_seq", sequenceName = "quote_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "phrase", nullable = false)
    private String phrase;

    public Quote(String phrase) {
        this.phrase = phrase;
    }
}

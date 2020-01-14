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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "phrase", nullable = false)
    private String phrase;
}

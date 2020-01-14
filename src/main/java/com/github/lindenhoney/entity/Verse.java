package com.github.lindenhoney.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "verse")
@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(exclude = {"quotes"})
@ToString(exclude = {"quotes"})
public class Verse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @OrderColumn(name = "index", nullable = false)
    @JoinColumn(name = "verse_id", referencedColumnName = "id", nullable = false, updatable = false)
    private List<Quote> quotes = new ArrayList<>();
}

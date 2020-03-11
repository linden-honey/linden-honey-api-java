package com.github.lindenhoney.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "verse")
@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(exclude = {"quotes"})
@ToString(exclude = {"quotes"})
public class VerseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @OrderColumn(name = "index", nullable = false)
    @JoinColumn(name = "verse_id", referencedColumnName = "id", nullable = false, updatable = false)
    private List<QuoteEntity> quotes = new ArrayList<>();
}

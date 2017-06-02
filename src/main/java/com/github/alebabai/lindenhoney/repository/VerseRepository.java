package com.github.alebabai.lindenhoney.repository;

import com.github.alebabai.lindenhoney.domain.Verse;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface VerseRepository extends Repository<Verse, Integer> {

    @Query(value = "SELECT * FROM VERSE ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Verse findRandomVerse();
}

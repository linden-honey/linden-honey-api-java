package com.github.lindenhoney.repository;

import com.github.lindenhoney.entity.Verse;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VerseRepository extends Repository<Verse, Integer> {

    @Query(value = "SELECT * FROM VERSE ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Verse findRandomVerse();

    @Query(value = "SELECT * FROM VERSE v WHERE v.song_id = :songId ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Verse findRandomVerseFromSong(@Param("songId") Integer songId);

    @Query(value = "SELECT * FROM VERSE v WHERE v.song_id = :songId", nativeQuery = true)
    List<Verse> findAllVersesFromSong(@Param("songId") Integer songId);
}

package com.github.lindenhoney.repository;

import com.github.lindenhoney.entity.VerseEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VerseRepository extends Repository<VerseEntity, Integer> {

    @Query(value = "SELECT * FROM VERSE ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Optional<VerseEntity> findRandomVerse();

    @Query(value = "SELECT * FROM VERSE v WHERE v.song_id = :songId ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Optional<VerseEntity> findRandomVerseFromSong(@Param("songId") Integer songId);

    @Query(value = "SELECT * FROM VERSE v WHERE v.song_id = :songId", nativeQuery = true)
    List<VerseEntity> findAllVersesFromSong(@Param("songId") Integer songId);
}

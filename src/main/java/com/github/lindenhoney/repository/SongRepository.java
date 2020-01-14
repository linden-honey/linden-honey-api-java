package com.github.lindenhoney.repository;

import com.github.lindenhoney.entity.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SongRepository extends PagingAndSortingRepository<Song, Integer> {

    @Query(value = "SELECT * FROM SONG ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Optional<Song> findRandomSong();

    Page<Song> findSongsByTitleContainingIgnoreCase(@Param("title") String title, Pageable pageable);

    Page<Song> findDistinctSongsByVersesQuotesPhraseContainingIgnoreCase(@Param("phrase") String phrase, Pageable pageable);
}

package com.github.alebabai.lindenhoney.repository;

import com.github.alebabai.lindenhoney.domain.Quote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface QuoteRepository extends Repository<Quote, Integer> {

    @Query(value = "SELECT * FROM QUOTE ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Quote findRandomQuote();

    @Query(value = "SELECT * FROM QUOTE q WHERE q.verse_id IN (SELECT v.id FROM VERSE v WHERE v.song_id = :songId) ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Quote findRandomQuoteFromSong(@Param("songId") Integer songId);

    Page<Quote> findQuotesByPhraseContainingIgnoreCase(@Param("phrase") String phrase, Pageable pageable);

    @Query(value = "SELECT * FROM QUOTE q WHERE q.verse_id IN (SELECT v.id FROM VERSE v WHERE v.song_id = :songId)", nativeQuery = true)
    List<Quote> findAllQuotesFromSong(@Param("songId") Integer songId);
}

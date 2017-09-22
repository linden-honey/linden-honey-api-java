package com.github.lindenhoney.repository;

import com.github.lindenhoney.domain.Song;
import com.github.lindenhoney.domain.projection.SongMin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(path = "songs", collectionResourceRel = "songs", excerptProjection = SongMin.class)
public interface SongRepository extends PagingAndSortingRepository<Song, Integer> {

    @RestResource(exported = false)
    @Override
    <S extends Song> S save(S song);

    @RestResource(exported = false)
    @Override
    void delete(Integer id);

    @RestResource(exported = false)
    @Override
    void delete(Song song);

    @RestResource(path = "random", rel = "random")
    @Query(value = "SELECT * FROM SONG ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Song findRandomSong();

    @RestResource(path = "by-title", rel = "byTitle")
    Page<Song> findSongsByTitleContainingIgnoreCase(@Param("title") String title, Pageable pageable);
}

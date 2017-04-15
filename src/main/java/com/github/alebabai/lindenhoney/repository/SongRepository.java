package com.github.alebabai.lindenhoney.repository;

import com.github.alebabai.lindenhoney.domain.Song;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "songs", collectionResourceRel = "songs")
public interface SongRepository extends PagingAndSortingRepository<Song, Long> {
}

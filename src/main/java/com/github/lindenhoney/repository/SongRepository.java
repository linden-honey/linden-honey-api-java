package com.github.lindenhoney.repository;

import com.github.lindenhoney.domain.Song;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface SongRepository extends ReactiveMongoRepository<Song, Integer> {

}

package com.github.alebabai.lindenhoney.repository;

import com.github.alebabai.lindenhoney.domain.Verse;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "verses", collectionResourceRel = "verses")
public interface VerseRepository extends PagingAndSortingRepository<Verse, Long> {
}

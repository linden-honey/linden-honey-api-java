package com.github.alebabai.lindenhoney.repository;

import com.github.alebabai.lindenhoney.domain.Quote;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "quotes", collectionResourceRel = "quotes")
public interface QuoteRepository extends PagingAndSortingRepository<Quote, Long> {
}

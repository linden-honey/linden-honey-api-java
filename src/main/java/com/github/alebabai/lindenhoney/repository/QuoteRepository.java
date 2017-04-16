package com.github.alebabai.lindenhoney.repository;

import com.github.alebabai.lindenhoney.domain.Quote;
import com.github.alebabai.lindenhoney.domain.Verse;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(path = "quotes", collectionResourceRel = "quotes")
public interface QuoteRepository extends PagingAndSortingRepository<Quote, Integer> {

    @RestResource(exported = false)
    @Override
    <S extends Quote> S save(S quote);

    @RestResource(exported = false)
    @Override
    void delete(Integer id);

    @RestResource(exported = false)
    @Override
    void delete(Quote quote);
}

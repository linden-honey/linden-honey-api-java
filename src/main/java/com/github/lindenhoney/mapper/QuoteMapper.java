package com.github.lindenhoney.mapper;

import com.github.lindenhoney.domain.Quote;
import com.github.lindenhoney.entity.QuoteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface QuoteMapper extends GenericMapper<Quote, QuoteEntity> {

    @Mapping(target = "id", ignore = true)
    QuoteEntity toEntity(Quote domain);

}

package com.github.lindenhoney.mapper;

import com.github.lindenhoney.domain.Quote;
import com.github.lindenhoney.entity.QuoteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface QuoteMapper {

    @Mapping(target = "id", ignore = true)
    QuoteEntity toEntity(Quote domain);

    Quote toDomain(QuoteEntity entity);
}

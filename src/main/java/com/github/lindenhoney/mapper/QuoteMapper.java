package com.github.lindenhoney.mapper;

import com.github.lindenhoney.domain.Quote;
import com.github.lindenhoney.entity.QuoteEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuoteMapper {
    QuoteEntity mapToEntity(Quote domain);

    Quote mapToDomain(QuoteEntity entity);
}

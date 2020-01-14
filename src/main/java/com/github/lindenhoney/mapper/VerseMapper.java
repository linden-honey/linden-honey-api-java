package com.github.lindenhoney.mapper;

import com.github.lindenhoney.domain.Verse;
import com.github.lindenhoney.entity.VerseEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {QuoteMapper.class})
public interface VerseMapper {
    VerseEntity mapToEntity(Verse domain);

    Verse mapToDomain(VerseEntity entity);
}

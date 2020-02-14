package com.github.lindenhoney.mapper;

import com.github.lindenhoney.domain.Verse;
import com.github.lindenhoney.entity.VerseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {QuoteMapper.class})
public interface VerseMapper extends GenericMapper<Verse, VerseEntity> {

    @Mapping(target = "id", ignore = true)
    VerseEntity toEntity(Verse domain);

    Verse toDomain(VerseEntity entity);
}

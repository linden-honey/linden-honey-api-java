package com.github.lindenhoney.mapper;

import com.github.lindenhoney.domain.Preview;
import com.github.lindenhoney.entity.SongEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PreviewMapper extends GenericMapper<Preview, SongEntity> {

    @Mapping(target = "author", ignore = true)
    @Mapping(target = "album", ignore = true)
    @Mapping(target = "verses", ignore = true)
    SongEntity toEntity(Preview domain);

}

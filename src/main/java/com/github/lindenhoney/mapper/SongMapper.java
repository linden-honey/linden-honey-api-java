package com.github.lindenhoney.mapper;

import com.github.lindenhoney.domain.Preview;
import com.github.lindenhoney.domain.Song;
import com.github.lindenhoney.entity.SongEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {VerseMapper.class})
public interface SongMapper {

    SongEntity toEntity(Song entity);

    Song toDomain(SongEntity entity);

    Preview toDomainPreview(Song entity);

    Preview toDomainPreview(SongEntity entity);
}

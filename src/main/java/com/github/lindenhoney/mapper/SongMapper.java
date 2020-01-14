package com.github.lindenhoney.mapper;

import com.github.lindenhoney.domain.Preview;
import com.github.lindenhoney.domain.Song;
import com.github.lindenhoney.entity.SongEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {VerseMapper.class})
public interface SongMapper {
    SongEntity mapToEntity(Song entity);

    Song mapToDomain(SongEntity entity);

    Preview mapToPreview(Song entity);

    Preview mapToPreview(SongEntity entity);
}

package org.example.domain.mapper;

import org.example.domain.entity.ExpandedFilmEntity;
import org.example.domain.entity.PreviewFilmEntity;
import org.example.domain.model.ExpandedFilmDTO;
import org.example.domain.model.PreviewFilmDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FilmMapper {
    
    @Mapping(target = "id", ignore = true)
    PreviewFilmEntity previewFilmDTOToEntity(PreviewFilmDTO previewFilmDTO);

    @Mapping(target = "id", ignore = true)
    ExpandedFilmEntity expandedFilmDTOToEntity(ExpandedFilmDTO expandedFilmDTO);

    @Mapping(target = "id", ignore = true)
    ExpandedFilmEntity previewFilmEntityToexpandedFilmEntity(PreviewFilmEntity previewFilmEntity);

    ExpandedFilmDTO expandedFilmEntityToDTO(ExpandedFilmEntity expandedFilmEntity);
}
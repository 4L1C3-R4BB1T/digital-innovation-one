package one.digitalinnovation.mangapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import one.digitalinnovation.mangapi.dto.MangaDTO;
import one.digitalinnovation.mangapi.entity.Manga;

@Mapper
public interface MangaMapper {

	MangaMapper INSTANCE = Mappers.getMapper(MangaMapper.class);

    Manga toModel(MangaDTO mangaDTO);

    MangaDTO toDTO(Manga manga);
	
}

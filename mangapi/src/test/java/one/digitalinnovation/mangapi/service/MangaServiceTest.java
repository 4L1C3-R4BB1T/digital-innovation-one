package one.digitalinnovation.mangapi.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import one.digitalinnovation.mangapi.builder.MangaDTOBuilder;
import one.digitalinnovation.mangapi.dto.MangaDTO;
import one.digitalinnovation.mangapi.entity.Manga;
import one.digitalinnovation.mangapi.exception.MangaAlreadyRegisteredException;
import one.digitalinnovation.mangapi.mapper.MangaMapper;
import one.digitalinnovation.mangapi.repository.MangaRepository;

@ExtendWith(MockitoExtension.class)
public class MangaServiceTest {
		
	@Mock
    private MangaRepository mangaRepository;

    private MangaMapper mangaMapper = MangaMapper.INSTANCE;
    
    @InjectMocks
    private MangaService mangaService;
    
    @Test
    void whenMangaInformedThenItShouldBeCreated() throws MangaAlreadyRegisteredException {
        // given
        MangaDTO expectedMangaDTO = MangaDTOBuilder.builder().build().toMangaDTO();
        Manga expectedSavedManga = mangaMapper.toModel(expectedMangaDTO);

        // when
        when(mangaRepository.findByName(expectedMangaDTO.getName())).thenReturn(Optional.empty());
        when(mangaRepository.save(expectedSavedManga)).thenReturn(expectedSavedManga);

        // then
        MangaDTO createdMangaDTO = mangaService.createManga(expectedMangaDTO);

        assertThat(createdMangaDTO.getId(), is(equalTo(expectedMangaDTO.getId())));
        assertThat(createdMangaDTO.getName(), is(equalTo(expectedMangaDTO.getName())));
        assertThat(createdMangaDTO.getQuantity(), is(equalTo(expectedMangaDTO.getQuantity())));
    }
    
    @Test
    void whenAlreadyRegisteredMangaInformedThenAnExceptionShouldBeThrown() {
        // given
    	MangaDTO expectedMangaDTO = MangaDTOBuilder.builder().build().toMangaDTO();
        Manga duplicatedManga = mangaMapper.toModel(expectedMangaDTO);

        // when
        when(mangaRepository.findByName(expectedMangaDTO.getName())).thenReturn(Optional.of(duplicatedManga));

        // then
        assertThrows(MangaAlreadyRegisteredException.class, () -> mangaService.createManga(expectedMangaDTO));
    }
    
}

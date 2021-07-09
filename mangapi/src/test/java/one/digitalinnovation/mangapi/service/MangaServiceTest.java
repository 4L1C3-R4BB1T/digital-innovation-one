package one.digitalinnovation.mangapi.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
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
import one.digitalinnovation.mangapi.exception.MangaNotFoundException;
import one.digitalinnovation.mangapi.exception.MangaStockExceededException;
import one.digitalinnovation.mangapi.mapper.MangaMapper;
import one.digitalinnovation.mangapi.repository.MangaRepository;

@ExtendWith(MockitoExtension.class)
public class MangaServiceTest {
		
    private static final long INVALID_MANGA_ID = 1L;
	
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
    
    @Test
    void whenValidMangaNameIsGivenThenReturnAManga() throws MangaNotFoundException {
        // given
        MangaDTO expectedFoundMangaDTO = MangaDTOBuilder.builder().build().toMangaDTO();
        Manga expectedFoundManga = mangaMapper.toModel(expectedFoundMangaDTO);

        // when
        when(mangaRepository.findByName(expectedFoundManga.getName())).thenReturn(Optional.of(expectedFoundManga));

        // then
        MangaDTO foundMangaDTO = mangaService.findByName(expectedFoundMangaDTO.getName());

        assertThat(foundMangaDTO, is(equalTo(expectedFoundMangaDTO)));
    }
    
    @Test
    void whenNotRegisteredMangaNameIsGivenThenThrowAnException() {
        // given
    	MangaDTO expectedFoundMangaDTO = MangaDTOBuilder.builder().build().toMangaDTO();

        // when
        when(mangaRepository.findByName(expectedFoundMangaDTO.getName())).thenReturn(Optional.empty());

        // then
        assertThrows(MangaNotFoundException.class, () -> mangaService.findByName(expectedFoundMangaDTO.getName()));
    }
    
    @Test
    void whenListMangaIsCalledThenReturnAListOfMangas() {
        // given
    	MangaDTO expectedFoundMangaDTO = MangaDTOBuilder.builder().build().toMangaDTO();
        Manga expectedFoundManga = mangaMapper.toModel(expectedFoundMangaDTO);

        // when
        when(mangaRepository.findAll()).thenReturn(Collections.singletonList(expectedFoundManga));

        // then
        List<MangaDTO> foundListMangasDTO = mangaService.listAll();

        assertThat(foundListMangasDTO, is(not(empty())));
        assertThat(foundListMangasDTO.get(0), is(equalTo(expectedFoundMangaDTO)));
    }
    
    @Test
    void whenListMangaIsCalledThenReturnAnEmptyListOfMangas() {
        // when
        when(mangaRepository.findAll()).thenReturn(Collections.emptyList());

        // then
        List<MangaDTO> foundListMangasDTO = mangaService.listAll();

        assertThat(foundListMangasDTO, is(empty()));
    }
    
    @Test
    void whenExclusionIsCalledWithValidIdThenAMangaShouldBeDeleted() throws MangaNotFoundException{
        // given
        MangaDTO expectedDeletedMangaDTO = MangaDTOBuilder.builder().build().toMangaDTO();
        Manga expectedDeletedManga = mangaMapper.toModel(expectedDeletedMangaDTO);

        // when
        when(mangaRepository.findById(expectedDeletedMangaDTO.getId())).thenReturn(Optional.of(expectedDeletedManga));
        doNothing().when(mangaRepository).deleteById(expectedDeletedMangaDTO.getId());

        // then
        mangaService.deleteById(expectedDeletedMangaDTO.getId());

        verify(mangaRepository, times(1)).findById(expectedDeletedMangaDTO.getId());
        verify(mangaRepository, times(1)).deleteById(expectedDeletedMangaDTO.getId());
    }
    
    @Test
    void whenExclusionIsCalledWithInvalidIdThenExceptionShouldBeThrown() {
        // when
    	when(mangaRepository.findById(INVALID_MANGA_ID)).thenReturn(Optional.empty());

    	// then
        assertThrows(MangaNotFoundException.class, () -> mangaService.deleteById(INVALID_MANGA_ID));
    }
    
    @Test
    void whenIncrementIsCalledThenIncrementMangaStock() throws MangaNotFoundException, MangaStockExceededException {
    	// given 
    	MangaDTO expectedMangaDTO = MangaDTOBuilder.builder().build().toMangaDTO();
        Manga expectedManga = mangaMapper.toModel(expectedMangaDTO);

        // when 
        when(mangaRepository.findById(expectedMangaDTO.getId())).thenReturn(Optional.of(expectedManga));
        when(mangaRepository.save(expectedManga)).thenReturn(expectedManga);

        int quantityToIncrement = 10;
        int expectedQuantityAfterIncrement = expectedMangaDTO.getQuantity() + quantityToIncrement;
       
        // then
        MangaDTO incrementedMangaDTO = mangaService.increment(expectedMangaDTO.getId(), quantityToIncrement);

        assertThat(incrementedMangaDTO.getQuantity(), is(equalTo(expectedQuantityAfterIncrement)));
        assertThat(expectedMangaDTO.getMax(), is(greaterThan(expectedQuantityAfterIncrement)));
    }
    
    @Test
    void whenIncrementIsGreaterThanMaxThenThrowException() {
        // given
    	MangaDTO expectedMangaDTO = MangaDTOBuilder.builder().build().toMangaDTO();
        Manga expectedManga = mangaMapper.toModel(expectedMangaDTO);

        // when
        when(mangaRepository.findById(expectedMangaDTO.getId())).thenReturn(Optional.of(expectedManga));

        int quantityToIncrement = 80;
        
        // then
        assertThrows(MangaStockExceededException.class, () -> mangaService.increment(expectedMangaDTO.getId(), quantityToIncrement));
    }
    
    @Test
    void whenIncrementIsCalledWithInvalidIdThenThrowException() {
        int quantityToIncrement = 10;

        // when
        when(mangaRepository.findById(INVALID_MANGA_ID)).thenReturn(Optional.empty());

        // then
        assertThrows(MangaNotFoundException.class, () -> mangaService.increment(INVALID_MANGA_ID, quantityToIncrement));
    }
    
    @Test
    void whenDecrementIsCalledThenDecrementMangaStock() throws MangaNotFoundException, MangaStockExceededException {
    	// given
    	MangaDTO expectedMangaDTO = MangaDTOBuilder.builder().build().toMangaDTO();
        Manga expectedManga = mangaMapper.toModel(expectedMangaDTO);

        // when
        when(mangaRepository.findById(expectedMangaDTO.getId())).thenReturn(Optional.of(expectedManga));
        when(mangaRepository.save(expectedManga)).thenReturn(expectedManga);

        int quantityToDecrement = 5;
        int expectedQuantityAfterDecrement = expectedMangaDTO.getQuantity() - quantityToDecrement;
        
        // then
        MangaDTO decrementedMangaDTO = mangaService.decrement(expectedMangaDTO.getId(), quantityToDecrement);

        assertThat(decrementedMangaDTO.getQuantity(), is(equalTo(expectedQuantityAfterDecrement)));
        assertThat(expectedQuantityAfterDecrement, is(greaterThan(0)));
    }
    
    @Test
    void whenDecrementIsCalledToEmptyStockThenEmptyMangaStock() throws MangaNotFoundException, MangaStockExceededException {
        // given
    	MangaDTO expectedMangaDTO = MangaDTOBuilder.builder().build().toMangaDTO();
        Manga expectedManga = mangaMapper.toModel(expectedMangaDTO);

        // when
        when(mangaRepository.findById(expectedMangaDTO.getId())).thenReturn(Optional.of(expectedManga));
        when(mangaRepository.save(expectedManga)).thenReturn(expectedManga);

        int quantityToDecrement = 10;
        int expectedQuantityAfterDecrement = expectedMangaDTO.getQuantity() - quantityToDecrement;
        
        // then
        MangaDTO decrementedMangaDTO = mangaService.decrement(expectedMangaDTO.getId(), quantityToDecrement);

        assertThat(expectedQuantityAfterDecrement, is(equalTo(0)));
        assertThat(expectedQuantityAfterDecrement, is(equalTo(decrementedMangaDTO.getQuantity())));
    }
    
    @Test
    void whenDecrementIsLowerThanZeroThenThrowException() {
        // given
    	MangaDTO expectedMangaDTO = MangaDTOBuilder.builder().build().toMangaDTO();
        Manga expectedManga = mangaMapper.toModel(expectedMangaDTO);

        // when
        when(mangaRepository.findById(expectedMangaDTO.getId())).thenReturn(Optional.of(expectedManga));

        int quantityToDecrement = 80;
        
        // then
        assertThrows(MangaStockExceededException.class, () -> mangaService.decrement(expectedMangaDTO.getId(), quantityToDecrement));
    }
    
    @Test
    void whenDecrementIsCalledWithInvalidIdThenThrowException() {
        int quantityToDecrement = 10;

        // when
        when(mangaRepository.findById(INVALID_MANGA_ID)).thenReturn(Optional.empty());

        // then
        assertThrows(MangaNotFoundException.class, () -> mangaService.decrement(INVALID_MANGA_ID, quantityToDecrement));
    }
    
}

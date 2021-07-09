package one.digitalinnovation.mangapi.controller;

import static one.digitalinnovation.mangapi.utils.JsonConvertionUtils.asJsonString;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import one.digitalinnovation.mangapi.builder.MangaDTOBuilder;
import one.digitalinnovation.mangapi.dto.MangaDTO;
import one.digitalinnovation.mangapi.dto.QuantityDTO;
import one.digitalinnovation.mangapi.exception.MangaNotFoundException;
import one.digitalinnovation.mangapi.exception.MangaStockExceededException;
import one.digitalinnovation.mangapi.service.MangaService;

@ExtendWith(MockitoExtension.class)
public class MangaControllerTest {
	
	private static final String MANGA_API_URL_PATH = "/api/v1/mangas";
	private static final String MANGA_API_SUBPATH_INCREMENT_URL = "/increment";
    private static final long VALID_MANGA_ID = 1L;
    private static final long INVALID_MANGA_ID = 2l;

    private MockMvc mockMvc;

    @Mock
    private MangaService mangaService;

    @InjectMocks
    private MangaController mangaController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(mangaController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }
    
    @Test
    void whenPOSTIsCalledThenAMangaIsCreated() throws Exception {
        // given
        MangaDTO mangaDTO = MangaDTOBuilder.builder().build().toMangaDTO();

        // when
        when(mangaService.createManga(mangaDTO)).thenReturn(mangaDTO);

        // then
        mockMvc.perform(post(MANGA_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(mangaDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(mangaDTO.getName())))
                .andExpect(jsonPath("$.publisher", is(mangaDTO.getPublisher())))
                .andExpect(jsonPath("$.genre", is(mangaDTO.getGenre().toString())));
    }
    
    @Test
    void whenPOSTIsCalledWithoutRequiredFieldThenAnErrorIsReturned() throws Exception {
        // given
        MangaDTO mangaDTO = MangaDTOBuilder.builder().build().toMangaDTO();
        mangaDTO.setPublisher(null);

        // then
        mockMvc.perform(post(MANGA_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(mangaDTO)))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    void whenGETIsCalledWithValidNameThenOkStatusIsReturned() throws Exception {
        // given
        MangaDTO mangaDTO = MangaDTOBuilder.builder().build().toMangaDTO();

        // when
        when(mangaService.findByName(mangaDTO.getName())).thenReturn(mangaDTO);

        // then
        mockMvc.perform(get(MANGA_API_URL_PATH + "/" + mangaDTO.getName())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(mangaDTO.getName())))
                .andExpect(jsonPath("$.publisher", is(mangaDTO.getPublisher())))
                .andExpect(jsonPath("$.genre", is(mangaDTO.getGenre().toString())));
    }
    
    @Test
    void whenGETIsCalledWithoutRegisteredNameThenNotFoundStatusIsReturned() throws Exception {
        // given
    	MangaDTO mangaDTO = MangaDTOBuilder.builder().build().toMangaDTO();

        // when
        when(mangaService.findByName(mangaDTO.getName())).thenThrow(MangaNotFoundException.class);

        // then
        mockMvc.perform(get(MANGA_API_URL_PATH + "/" + mangaDTO.getName())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    
    @Test
    void whenGETListWithMangasIsCalledThenOkStatusIsReturned() throws Exception {
        // given
    	MangaDTO mangaDTO = MangaDTOBuilder.builder().build().toMangaDTO();

        // when
        when(mangaService.listAll()).thenReturn(Collections.singletonList(mangaDTO));

        // then
        mockMvc.perform(get(MANGA_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is(mangaDTO.getName())))
                .andExpect(jsonPath("$[0].publisher", is(mangaDTO.getPublisher())))
                .andExpect(jsonPath("$[0].genre", is(mangaDTO.getGenre().toString())));
    }
    
    @Test
    void whenGETListWithoutMangasIsCalledThenOkStatusIsReturned() throws Exception {
        // when
    	when(mangaService.listAll()).thenReturn(Collections.emptyList());

    	// then
        mockMvc.perform(get(MANGA_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    
    @Test
    void whenDELETEIsCalledWithValidIdThenNoContentStatusIsReturned() throws Exception {
        // when
    	doNothing().when(mangaService).deleteById(VALID_MANGA_ID);

    	// then
        mockMvc.perform(delete(MANGA_API_URL_PATH + "/" + VALID_MANGA_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(mangaService, times(1)).deleteById(VALID_MANGA_ID);
    }
    
    @Test
    void whenDELETEIsCalledWithoutValidIdThenNotFoundStatusIsReturned() throws Exception {
        // when
    	doThrow(MangaNotFoundException.class).when(mangaService).deleteById(INVALID_MANGA_ID);

    	// then
        mockMvc.perform(delete(MANGA_API_URL_PATH + "/" + INVALID_MANGA_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    
    @Test
    void whenPATCHIsCalledToIncrementStockThenOKstatusIsReturned() throws Exception {
        // given
    	QuantityDTO quantityDTO = QuantityDTO.builder().quantity(10).build();

        MangaDTO mangaDTO = MangaDTOBuilder.builder().build().toMangaDTO();
        mangaDTO.setQuantity(mangaDTO.getQuantity() + quantityDTO.getQuantity());

        // when
        when(mangaService.increment(VALID_MANGA_ID, quantityDTO.getQuantity())).thenReturn(mangaDTO);

        // then
        mockMvc.perform(patch(MANGA_API_URL_PATH + "/" + VALID_MANGA_ID + MANGA_API_SUBPATH_INCREMENT_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(quantityDTO))).andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(mangaDTO.getName())))
                .andExpect(jsonPath("$.publisher", is(mangaDTO.getPublisher())))
                .andExpect(jsonPath("$.genre", is(mangaDTO.getGenre().toString())))
                .andExpect(jsonPath("$.quantity", is(mangaDTO.getQuantity())));
    }
    
    @Test
    void whenPATCHIsCalledToIncrementGreaterThanMaxThenBadRequestStatusIsReturned() throws Exception {
        // given
    	QuantityDTO quantityDTO = QuantityDTO.builder().quantity(30).build();

        MangaDTO mangaDTO = MangaDTOBuilder.builder().build().toMangaDTO();
        mangaDTO.setQuantity(mangaDTO.getQuantity() + quantityDTO.getQuantity());

        // when
        when(mangaService.increment(VALID_MANGA_ID, quantityDTO.getQuantity())).thenThrow(MangaStockExceededException.class);

        // then
        mockMvc.perform(patch(MANGA_API_URL_PATH + "/" + VALID_MANGA_ID + MANGA_API_SUBPATH_INCREMENT_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(quantityDTO))).andExpect(status().isBadRequest());
    }
    
    @Test
    void whenPATCHIsCalledWithInvalidMangaIdToIncrementThenNotFoundStatusIsReturned() throws Exception {
    	// given
    	QuantityDTO quantityDTO = QuantityDTO.builder().quantity(30).build();

    	// when
        when(mangaService.increment(INVALID_MANGA_ID, quantityDTO.getQuantity())).thenThrow(MangaNotFoundException.class);
       
        // then
        mockMvc.perform(patch(MANGA_API_URL_PATH + "/" + INVALID_MANGA_ID + MANGA_API_SUBPATH_INCREMENT_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(quantityDTO)))
                .andExpect(status().isNotFound());
    }
    
}

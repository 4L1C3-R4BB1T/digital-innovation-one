package one.digitalinnovation.mangapi.controller;

import static one.digitalinnovation.mangapi.utils.JsonConvertionUtils.asJsonString;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import one.digitalinnovation.mangapi.exception.MangaNotFoundException;
import one.digitalinnovation.mangapi.service.MangaService;

@ExtendWith(MockitoExtension.class)
public class MangaControllerTest {
	
	private static final String MANGA_API_URL_PATH = "/api/v1/mangas";
   
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

        //when
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

        //when
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

        //when
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
        // given
    	MangaDTO mangaDTO = MangaDTOBuilder.builder().build().toMangaDTO();

        //when
        when(mangaService.listAll()).thenReturn(Collections.singletonList(mangaDTO));

        // then
        mockMvc.perform(get(MANGA_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    
}

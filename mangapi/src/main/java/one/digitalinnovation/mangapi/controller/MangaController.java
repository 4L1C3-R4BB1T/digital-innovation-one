package one.digitalinnovation.mangapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import one.digitalinnovation.mangapi.controller.openapi.MangaControllerOpenApi;
import one.digitalinnovation.mangapi.dto.MangaDTO;
import one.digitalinnovation.mangapi.dto.QuantityDTO;
import one.digitalinnovation.mangapi.exception.MangaAlreadyRegisteredException;
import one.digitalinnovation.mangapi.exception.MangaNotFoundException;
import one.digitalinnovation.mangapi.exception.MangaStockExceededException;
import one.digitalinnovation.mangapi.service.MangaService;

@RestController
@RequestMapping("/api/v1/mangas")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MangaController implements MangaControllerOpenApi {

	private final MangaService mangaService;
	
	@PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MangaDTO createManga(@RequestBody @Valid MangaDTO mangaDTO) throws MangaAlreadyRegisteredException {
        return mangaService.createManga(mangaDTO);
    }
	
	@GetMapping("/{name}")
    public MangaDTO findByName(@PathVariable String name) throws MangaNotFoundException {
        return mangaService.findByName(name);
    }
	
	@GetMapping
    public List<MangaDTO> listMangas() {
        return mangaService.listAll();
    }
	
	@DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) throws MangaNotFoundException {
        mangaService.deleteById(id);
    }
	
	@PatchMapping("/{id}/increment")
    public MangaDTO increment(@PathVariable Long id, @RequestBody @Valid QuantityDTO quantityDTO) throws MangaNotFoundException, MangaStockExceededException {
        return mangaService.increment(id, quantityDTO.getQuantity());
    }
	
	@PatchMapping("/{id}/decrement")
    public MangaDTO decrement(@PathVariable Long id, @RequestBody @Valid QuantityDTO quantityDTO) throws MangaNotFoundException, MangaStockExceededException {
        return mangaService.decrement(id, quantityDTO.getQuantity());
    }
	
}

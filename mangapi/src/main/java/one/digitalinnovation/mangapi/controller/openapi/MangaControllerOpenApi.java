package one.digitalinnovation.mangapi.controller.openapi;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import one.digitalinnovation.mangapi.dto.MangaDTO;
import one.digitalinnovation.mangapi.dto.QuantityDTO;
import one.digitalinnovation.mangapi.exception.MangaAlreadyRegisteredException;
import one.digitalinnovation.mangapi.exception.MangaNotFoundException;
import one.digitalinnovation.mangapi.exception.MangaStockExceededException;

@Api(tags="Manga Controller")
public interface MangaControllerOpenApi {

	@ApiOperation(value = "Manga creation operation")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success manga creation"),
            @ApiResponse(code = 400, message = "Missing required fields or wrong field range value.")
    })
    MangaDTO createManga(MangaDTO mangaDTO) throws MangaAlreadyRegisteredException;

	@ApiOperation(value = "Returns manga found by a given name")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success manga found in the system"),
			@ApiResponse(code = 404, message = "Manga with given name not found.")
	})
	MangaDTO findByName(@PathVariable String name) throws MangaNotFoundException;
	
	@ApiOperation(value = "Returns a list of all mangas registered in the system")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "List of all mangas registered in the system"),
    })
    List<MangaDTO> listMangas();
	
	@ApiOperation(value = "Delete a manga found by a given valid Id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Success manga deleted in the system"),
            @ApiResponse(code = 404, message = "Manga with given id not found.")
    })
    void deleteById(@PathVariable Long id) throws MangaNotFoundException;
	
    @ApiOperation(value = "Increment manga by a given id quantity in a stock")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success manga incremented in stock"),
            @ApiResponse(code = 400, message = "Manga not successfully increment in stock"),
            @ApiResponse(code = 404, message = "Manga with given id not found.")
    })
    MangaDTO increment(@PathVariable Long id, @RequestBody @Valid QuantityDTO quantityDTO) throws MangaNotFoundException, MangaStockExceededException;
    
    @ApiOperation(value = "Decrement manga by a given id quantity in a stock")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success manga decremented in stock"),
            @ApiResponse(code = 400, message = "Manga not successfully decrement in stock"),
            @ApiResponse(code = 404, message = "Manga with given id not found.")
    })
    MangaDTO decrement(@PathVariable Long id, @RequestBody @Valid QuantityDTO quantityDTO) throws MangaNotFoundException, MangaStockExceededException;
	
}

package one.digitalinnovation.personapi.openapi;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.MediaType;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import one.digitalinnovation.personapi.dto.request.PersonDTO;
import one.digitalinnovation.personapi.dto.response.MessageResponseDTO;
import one.digitalinnovation.personapi.exception.PersonNotFoundException;

@Api(tags = "Person Controller")
public interface PersonControllerOpenApi {

	@ApiOperation("Create Person")
	@ApiResponses({ @ApiResponse(code = 201, message = "Created person with ID 1", response = PersonDTO.class) })	
	MessageResponseDTO createPerson(
			@ApiParam(name = "body", value = "Representation of a new person", required = true)
			@Valid PersonDTO personDTO);
	
	@ApiOperation(value = "List all persons", httpMethod = "GET")
	@ApiResponses({ @ApiResponse(code = 200, message = "List all persons", response = PersonDTO.class) })
	List<PersonDTO> listAll();
	
	@ApiOperation(value = "Find person by ID", httpMethod = "GET")
	@ApiResponses({ @ApiResponse(code = 200, message = "Find person by ID", response = PersonDTO.class),
			@ApiResponse(code = 404, message = "Person not found with ID 1", response = PersonNotFoundException.class) })
	@ApiImplicitParam(name = "id", value = "person ID", required = true, dataType = "int", paramType = "path", example = "1")
	PersonDTO findById(Long id) throws PersonNotFoundException;
	
	@ApiOperation(value = "Update person by ID", httpMethod = "PUT", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ @ApiResponse(code = 200, message = "Updated person with ID 1", response = PersonDTO.class),
			@ApiResponse(code = 404, message = "Person not found with ID 1", response = PersonNotFoundException.class) })
	@ApiImplicitParam(name = "id", value = "person ID", required = true, dataType = "int", paramType = "path", example = "1")
	MessageResponseDTO updateById(Long id, 
			@ApiParam(name = "body", value = "Representation of a person", required = true) 
			@Valid PersonDTO personDTO) throws PersonNotFoundException;
	
	@ApiOperation(value = "Delete person by ID", httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses({ @ApiResponse(code = 204, message = "Person deleted", response = PersonDTO.class),
			@ApiResponse(code = 404, message = "Person not found with ID 1", response = PersonNotFoundException.class) })
	@ApiImplicitParam(name = "id", value = "person ID", required = true, dataType = "int", paramType = "path", example = "1")
	void deleteById(Long id) throws PersonNotFoundException;
	
}

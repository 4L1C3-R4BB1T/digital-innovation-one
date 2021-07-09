package one.digitalinnovation.mangapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MangaStockExceededException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public MangaStockExceededException(Long id, int quantityToIncrement) {
        super(String.format("Manga with id %s to increment informed exceeds the max stock capacity: %s", id, quantityToIncrement));
    }

}

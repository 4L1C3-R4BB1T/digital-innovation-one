package one.digitalinnovation.mangapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MangaNotFoundException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public MangaNotFoundException(String mangaName) {
        super(String.format("Manga with name %s not found in the system.", mangaName));
    }

    public MangaNotFoundException(Long id) {
        super(String.format("Manga with id %s not found in the system.", id));
    }

}

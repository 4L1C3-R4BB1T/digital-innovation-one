package one.digitalinnovation.mangapi.builder;

import lombok.Builder;
import one.digitalinnovation.mangapi.dto.MangaDTO;
import one.digitalinnovation.mangapi.enums.MangaGenre;

@Builder
public class MangaDTOBuilder {

	@Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String name = "Ultramaniac";

    @Builder.Default
    private String publisher = "Panini";

    @Builder.Default
    private int max = 50;

    @Builder.Default
    private int quantity = 10;

    @Builder.Default
    private MangaGenre genre = MangaGenre.FANTASY;

    public MangaDTO toMangaDTO() {
        return new MangaDTO(id, name, publisher, max, quantity, genre);
    }
	
}

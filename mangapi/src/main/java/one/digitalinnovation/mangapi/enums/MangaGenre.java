package one.digitalinnovation.mangapi.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MangaGenre {

    COMEDY("Comedy"),
    DRAMA("Drama"),
    FANTASY("Fantasy"),
    MAGIC("Magic"),
    MYSTERY("Mystery"),
    ROMANCE("Romance"),
    SCI_FI("Sci-Fi"),
    SCHOOL("School"),
    SLICE_OF_LIFE("Slice of Life"),
    SUPERNATURAL("Supernatural");

    private final String description;
    
}


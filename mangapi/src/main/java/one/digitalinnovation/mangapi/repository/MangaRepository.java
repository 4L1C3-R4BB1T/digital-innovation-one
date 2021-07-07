package one.digitalinnovation.mangapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import one.digitalinnovation.mangapi.entity.Manga;

public interface MangaRepository extends JpaRepository<Manga, Long> {
	
    Optional<Manga> findByName(String name);
    
}

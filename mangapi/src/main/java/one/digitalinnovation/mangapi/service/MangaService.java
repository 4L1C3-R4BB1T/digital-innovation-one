package one.digitalinnovation.mangapi.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import one.digitalinnovation.mangapi.dto.MangaDTO;
import one.digitalinnovation.mangapi.entity.Manga;
import one.digitalinnovation.mangapi.exception.MangaAlreadyRegisteredException;
import one.digitalinnovation.mangapi.exception.MangaNotFoundException;
import one.digitalinnovation.mangapi.exception.MangaStockExceededException;
import one.digitalinnovation.mangapi.mapper.MangaMapper;
import one.digitalinnovation.mangapi.repository.MangaRepository;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MangaService {
	
	private final MangaRepository mangaRepository;
    private final MangaMapper mangaMapper = MangaMapper.INSTANCE;
    
    public MangaDTO createManga(MangaDTO mangaDTO) throws MangaAlreadyRegisteredException {
        verifyIfIsAlreadyRegistered(mangaDTO.getName());
        Manga manga = mangaMapper.toModel(mangaDTO);
        Manga savedManga = mangaRepository.save(manga);
        return mangaMapper.toDTO(savedManga);
    }
    
    public MangaDTO findByName(String name) throws MangaNotFoundException {
        Manga foundManga = mangaRepository.findByName(name)
                .orElseThrow(() -> new MangaNotFoundException(name));
        return mangaMapper.toDTO(foundManga);
    }
    
    public List<MangaDTO> listAll() {
        return mangaRepository.findAll()
                .stream()
                .map(mangaMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    public void deleteById(Long id) throws MangaNotFoundException {
        verifyIfExists(id);
        mangaRepository.deleteById(id);
    }
    
    private void verifyIfIsAlreadyRegistered(String name) throws MangaAlreadyRegisteredException {
        Optional<Manga> optSavedManga = mangaRepository.findByName(name);
        if (optSavedManga.isPresent()) {
            throw new MangaAlreadyRegisteredException(name);
        }
    }
    
    private Manga verifyIfExists(Long id) throws MangaNotFoundException {
        return mangaRepository.findById(id)
                .orElseThrow(() -> new MangaNotFoundException(id));
    }
    
    public MangaDTO increment(Long id, int quantityToIncrement) throws MangaNotFoundException, MangaStockExceededException {
        Manga mangaToIncrementStock = verifyIfExists(id);
        int quantityAfterIncrement = quantityToIncrement + mangaToIncrementStock.getQuantity();
        if (quantityAfterIncrement <= mangaToIncrementStock.getMax()) {
            mangaToIncrementStock.setQuantity(mangaToIncrementStock.getQuantity() + quantityToIncrement);
            Manga incrementedMangaStock = mangaRepository.save(mangaToIncrementStock);
            return mangaMapper.toDTO(incrementedMangaStock);
        }
        throw new MangaStockExceededException(id, quantityToIncrement);
    }

}

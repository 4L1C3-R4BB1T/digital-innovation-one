package com.patterns.design.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.patterns.design.entities.Character;
import com.patterns.design.repositories.CharacterRepository;

@Service
public class CharacterService implements CharacterServiceStrategy {

	@Autowired
	private CharacterRepository characterRepository;
	
	@Override
	public List<Character> findAll() {
		return characterRepository.findAll();
	}

	@Override
	public Character findById(Long id) {
		return characterRepository.findById(id).orElse(null);
	}

	@Override
	public Character create(Character character) {
		return characterRepository.save(character);
	}

	@Override
	public Character update(Long id, Character character) {
		Optional<Character> findChar = characterRepository.findById(id);
		if (findChar.isPresent()) {
			return characterRepository.save(character);
		}
		return null;
	}

	@Override
	public void delete(Long id) {
		characterRepository.deleteById(id);
	}

}

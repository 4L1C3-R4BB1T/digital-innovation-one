package com.patterns.design.services;

import java.util.List;

import com.patterns.design.entities.Character;

public interface CharacterServiceStrategy {
	
	List<Character> findAll();

	Character findById(Long id);

	Character create(Character cliente);

	Character update(Long id, Character cliente);

	void delete(Long id);

}

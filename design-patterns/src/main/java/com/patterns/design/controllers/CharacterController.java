package com.patterns.design.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.patterns.design.entities.Character;
import com.patterns.design.services.CharacterService;

@RestController
@RequestMapping("characters")
public class CharacterController {
	
	@Autowired
	private CharacterService characterService;
	
	@GetMapping
	public List<Character> findAll() {
		return characterService.findAll();
	}
	
	@GetMapping("/{id}")
	public Character findById(@PathVariable Long id) {
		return characterService.findById(id);
	}

	@PostMapping
	public Character create(@RequestBody Character character) {
		return characterService.create(character);
	}
	
	@PutMapping("/{id}")
	public Character update(@PathVariable Long id, @RequestBody Character character) {
		return characterService.update(id, character);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		characterService.delete(id);
	}
	
}

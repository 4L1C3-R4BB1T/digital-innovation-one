package com.patterns.design.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.patterns.design.entities.Character;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Long> {

}

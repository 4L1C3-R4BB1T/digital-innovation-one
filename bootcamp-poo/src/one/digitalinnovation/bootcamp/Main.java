package one.digitalinnovation.bootcamp;

import java.time.LocalDate;

import one.digitalinnovation.bootcamp.domain.Curso;
import one.digitalinnovation.bootcamp.domain.Mentoria;

public class Main {
	
	public static void main(String[] args) {
		
		Curso c1 = new Curso("Desafio POO", "Abstraindo um Bootcamp usando Orientação a Objetos em Java", 1);
		Curso c2 = new Curso("Dominando IDEs Java", "Aprenda a utilizar as principais IDEs Java", 4);

		Mentoria m1 = new Mentoria("Mentoria Java", "Aprenda os fundamentos da linguagem de programação Java", LocalDate.now());
		
		System.out.println(c1);
		System.out.println(c2);
		System.out.println(m1);
		
	}
	
}

package one.digitalinnovation.bootcamp;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

import one.digitalinnovation.bootcamp.domain.Bootcamp;
import one.digitalinnovation.bootcamp.domain.Conteudo;
import one.digitalinnovation.bootcamp.domain.Curso;
import one.digitalinnovation.bootcamp.domain.Dev;
import one.digitalinnovation.bootcamp.domain.Mentoria;

public class Main {
	
	public static void main(String[] args) {
		
		Curso c1 = new Curso("Desafio POO", "Abstraindo um Bootcamp usando Orientação a Objetos em Java", 1);
		Conteudo c2 = new Curso("Dominando IDEs Java", "Aprenda a utilizar as principais IDEs Java", 4);

		Mentoria m1 = new Mentoria("Mentoria Java", "Aprenda os fundamentos da linguagem de programação Java", LocalDate.now());
		
		Set<Conteudo> conteudos = new LinkedHashSet<>();
		conteudos.add(c1);
		conteudos.add(c2);
		conteudos.add(m1);
		
		Bootcamp bootcamp = new Bootcamp("Java Developer", "Aprenda a programar em Java", conteudos);
		
		Dev d1 = new Dev("Joao");
		Dev d2 = new Dev("Maria");
		
		d1.inscreverBootcamp(bootcamp);
		d2.inscreverBootcamp(bootcamp);
		
		System.out.println("CONTEUDOS INSCRITOS DEV1\n" + d1.getConteudosInscritos() + "\n");
		System.out.println("XP: " + d1.calcularXp() + "\n");
		
		System.out.println("CONTEUDOS INSCRITOS DEV2\n" + d2.getConteudosInscritos() + "\n");
		System.out.println("XP: " + d2.calcularXp() + "\n");
		
		System.out.println("----------------------------------------------\n");
		
		d1.progredir();
		d2.progredir();
		d2.progredir();
		
		System.out.println("CONTEUDOS CONCLUIDOS DEV1\n" + d1.getConteudosConcluidos() + "\n");		
		System.out.println("XP: " + d1.calcularXp() + "\n");

		System.out.println("CONTEUDOS CONCLUIDOS DEV2\n" + d2.getConteudosConcluidos() + "\n");
		System.out.println("XP: " + d2.calcularXp() + "\n");

		System.out.println("----------------------------------------------\n");
		
		System.out.println("CONTEUDOS INSCRITOS DEV1\n" + d1.getConteudosInscritos() + "\n");
		System.out.println("CONTEUDOS INSCRITOS DEV2\n" + d2.getConteudosInscritos() + "\n");
		
	}
	
}

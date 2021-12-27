package one.digitalinnovation.bootcamp.domain;

import java.time.LocalDate;

public class Mentoria extends Conteudo {
	
	private LocalDate data;
	
	public Mentoria() {
		super();
	}
	
	public Mentoria(String titulo, String descricao, LocalDate data) {
		super(titulo, descricao);
		this.data = data;
	}

	public LocalDate getData() {
		return data;
	}
	
	public void setData(LocalDate data) {
		this.data = data;
	}
	
	@Override
	public double calcularXp() {
		return XP_PADRAO + 20d;
	}

	@Override
	public String toString() {
		return "MENTORIA\nTitulo: " + getTitulo() 
				+ "\nDescricao: " + getDescricao() 
				+ "\nData: " + data + "\n";
	}
	
}

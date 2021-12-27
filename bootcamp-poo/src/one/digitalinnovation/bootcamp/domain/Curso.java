package one.digitalinnovation.bootcamp.domain;

public class Curso {
	
	private String titulo;
	private String descricao;
	private Integer cargaHoraria;
	
	public Curso() {
		
	}
	
	public Curso(String titulo, String descricao, Integer cargaHoraria) {
		this.titulo = titulo;
		this.descricao = descricao;
		this.cargaHoraria = cargaHoraria;
	}

	public String getTitulo() {
		return titulo;
	}
	
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public Integer getCargaHoraria() {
		return cargaHoraria;
	}
	
	public void setCargaHoraria(Integer cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}

	@Override
	public String toString() {
		return "CURSO\nTitulo: " + titulo 
				+ "\nDescricao: " + descricao 
				+ "\nCarga Horaria: " + cargaHoraria + "\n";
	}
	
}

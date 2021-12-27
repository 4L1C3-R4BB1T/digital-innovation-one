package one.digitalinnovation.bootcamp.domain;

public abstract class Conteudo {
	
	private String titulo;
	private String descricao;
	
	protected static final Double XP_PADRAO = 10d;
	
	public Conteudo() {
		
	}
	
	public Conteudo(String titulo, String descricao) {
		this.titulo = titulo;
		this.descricao = descricao;
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
	
	public abstract double calcularXp();
	
}

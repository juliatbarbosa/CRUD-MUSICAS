package org.libertas;

public class Musica {	
	private int id;
	private String compositor, cantor, nomemusica, anolancamento, genero;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getCompositor() {
		return compositor;
	}
	
	public void setCompositor(String compositor) {
		this.compositor = compositor;
	}
	
	public String getCantor() {
		return cantor;
	}
	
	public void setCantor(String cantor) {
		this.cantor = cantor;
	}
	
	public String getNome() {
		return nomemusica;
	}
	
	public void setNome(String nomemusica) {
		this.nomemusica = nomemusica;
	}
	
	public String getAnoLancamento() {
		return anolancamento;
	}
	
	public void setAnoLancamento(String anolancamento) {
		this.anolancamento = anolancamento;
	}
	
	public String getGenero() {
		return genero;
	}
	
	public void setGenero(String genero) {
		this.genero = genero;
	}
	
	public String toString() {
		return "";
	}
}
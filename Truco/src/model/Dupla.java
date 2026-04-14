package model;

import java.util.List;

public class Dupla {
	
	private int id;
	private int pontuacao;
	private List<Jogador> dupla;
	
	public Dupla () {
		
		this.pontuacao = 0;
		
	}
	
	public int getPontuacao() {
		return this.pontuacao;
	}
	
	public void setPontucao(int pontuacao) {
		this.pontuacao = pontuacao;
	}
	
	public List<Jogador> getJogador(){
		return this.dupla;
	} 
	
	public void setDupla(List<Jogador> dupla) {
		this.dupla = dupla;
	}
	
	public void addDupla(Jogador jogador) {
		this.dupla.add(jogador);
	}
	
	public void addPontuacao(int pontos) {
		this.pontuacao = this.pontuacao + pontos;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}

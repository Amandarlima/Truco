package model;

import java.util.List;

public class Dupla {
	
	private int pontuacao;
	private List<Jogador> dupla;
	
	public Dupla () {
		
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
	
	public void addPontuacao(int pontos) {
		this.pontuacao = this.pontuacao + pontos;
	}
}

package model;

import java.util.ArrayList;
import java.util.List;

public class Jogador {
	
	private int id;
	private String nome;
	private List<Carta> mao = new ArrayList<>(); 
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public List<Carta> getMao() {
		return this.mao;
	}
	
	public void setMao(List<Carta> mao) {
		this.mao = mao;
	}
	
	public Carta jogarCarta(int i) {
	    if (i < 0 || i >= mao.size()) {
	        throw new IllegalArgumentException("Carta inválida");
	    }
	    return this.mao.remove(i);
	}
	
	public void receberCarta(Carta carta) {
		this.mao.add(carta);
	}
	
	private Dupla dupla;

	public Dupla getDupla() {
	    return dupla;
	}

	public void setDupla(Dupla dupla) {
	    this.dupla = dupla;
	}
}

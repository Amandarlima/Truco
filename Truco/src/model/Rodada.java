package model;

import java.util.ArrayList;
import java.util.List;

public class Rodada {

	List<Carta> monte = new ArrayList<>();
	int pontuacao;

	public Rodada() {
		this.pontuacao = 0;
		this.monte = new ArrayList<>();
	}

	public void trucarRodada() {
		if (this.pontuacao < 12) {
			this.pontuacao++;
		}
	}
	private Carta vira;

	public Carta getVira() {
	    return vira;
	}

	public void setVira(Carta vira) {
	    this.vira = vira;
	}

	private int valor = 1;

	public int getValor() {
	    return valor;
	}

	public void aumentarAposta() {
	    if (valor == 1) valor = 3;
	    else if (valor == 3) valor = 6;
	    else if (valor == 6) valor = 9;
	    else if (valor == 9) valor = 12;
	}
	
	private boolean trucoAtivo = false;

	public boolean isTrucoAtivo() {
	    return trucoAtivo;
	}

	public void setTrucoAtivo(boolean trucoAtivo) {
	    this.trucoAtivo = trucoAtivo;
	}
	public void setValor(int valor) {
	    this.valor = valor;
	}
}

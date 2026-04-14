package model;
import java.util.ArrayList;
import java.util.List;

public class Rodada {
	
	List<Carta> monte = new ArrayList<>();
	int pontuacao;
	
	public Rodada (){
		this.pontuacao =0;
		this.monte = new ArrayList<>();
	}
	
	public void trucarRodada () {
		if (this.pontuacao != 12) {
			this.pontuacao++;
		}
	}
	
	
	
	
	
}

package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Baralho {
	
	List<Carta> baralho = new ArrayList<>();
	
	//instancia o baralho
	public Baralho () {
		
		for (int i = 0; i < 4; i++) {
			for (int f = 10; f < 101; f = f +10) {
				
				Carta carta = new Carta();
				carta.setNaipePorValor(i);
				carta.setModeloPorPeso(f);
				carta.setPeso(f);
				this.baralho.add(carta);
				
			}
		}
		
		Collections.shuffle(baralho);
		System.out.println(baralho);
	}
	

	
}

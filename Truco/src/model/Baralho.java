package model;

import java.util.ArrayList;
import java.util.List;

public class Baralho {
	
	List<Carta> baralho = new ArrayList<>();
	
	//instancia o baralho
	public Baralho () {
		
		Carta carta = new Carta();
		
		for (int i = 0; i < 4; i++) {
			
			carta.setNaipePorValor(i);
			
			for (int f = 10; f < 101; f = f +10) {
				
				carta.setModeloPorPeso(f);
				carta.setPeso(f);
				this.baralho.add(carta);
				System.out.println(carta);
				
			}
		}
		
	}
	

	
}

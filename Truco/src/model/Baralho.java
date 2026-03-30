package model;

import java.util.ArrayList;
import java.util.List;

public class Baralho {
	
	List<Carta> baralho = new ArrayList<>();
	
	//instancia o baralho
	public Baralho () {
		
		
		for (int i = 0; i < 4; i++) {
			
			
			for (int f = 10; f < 101; f = f +10) {
				
				//coloquei dentro do for porque assim ele sempre faz uma carta nova e fora ele usa sempre a mesma e só altera o valor dela, aqui vai ter as 40
				Carta carta = new Carta();
				
				carta.setNaipePorValor(i);
				carta.setModeloPorPeso(f);
				carta.setPeso(f);
				this.baralho.add(carta);
				System.out.println(carta);
				
			}
		}
		
	}
	

	
}

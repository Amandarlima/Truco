package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Baralho {

	List<Carta> baralho = new ArrayList<>();

	// instancia o baralho
	// teste para merge
	public Baralho() {

		for (int i = 1; i < 4; i++) {
			for (int f = 10; f < 101; f = f + 10) {

				Carta carta = new Carta();
				carta.setNaipePorValor(i);
				carta.setModeloPorPeso(f);
				this.baralho.add(carta);

			}
		}

		Collections.shuffle(baralho);
		// System.out.println(baralho);
	}

	public List<Carta> getBaralho() {
		return this.baralho;
	}

	public List<Carta> entregarCartas() {
		List<Carta> cartas = new ArrayList<>();

		for (int i = 0; i < 3; i++) {
			cartas.add(this.baralho.remove(0));
		}
		return cartas;
	}

}

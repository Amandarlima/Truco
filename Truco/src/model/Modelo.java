package model;

public enum Modelo {

	QUATRO(1), CINCO(2), SEIS(3), SETE(4), RAINHA(5), VALETE(6), REI(7), AS(8), DOIS(9), TRES(10);

	private int peso;

	Modelo(int peso) {
		this.peso = peso;
	}

	public int getPeso() {
		return peso;
	}

	public static Modelo fromPeso(int peso) {
		for (Modelo m : Modelo.values()) {
			if (m.getPeso() == peso) {
				return m;
			}
		}
		throw new IllegalArgumentException("Nenhum modelo encontrado com o peso: " + peso);
	}

}

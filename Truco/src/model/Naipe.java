package model;

public enum Naipe {

	OUROS(0), ESPADAS(1), COPAS(2), PAUS(3);

	private final int peso;

	Naipe(int peso) {
		this.peso = peso;
	}

	public int getPeso() {
		return peso;
	}

	// Método de busca atualizado para refletir o novo nome da variável
	public static Naipe fromPeso(int peso) {
		for (Naipe n : Naipe.values()) {
			if (n.getPeso() == peso) {
				return n;
			}
		}
		throw new IllegalArgumentException("Nenhum naipe encontrado com o peso: " + peso);
	}

}

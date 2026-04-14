package model;

public enum Naipe {

	OUROS(1),
	ESPADAS(2),
	COPAS(3),
	PAUS(4);
	
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

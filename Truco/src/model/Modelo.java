package model;

public enum Modelo {

	QUATRO(10),
	CINCO(20),
	SEIS(30),
	SETE(40),
	RAINHA(50),
	VALETE(60),
	REI(70),
	AS(80),
	DOIS(90),
	TRES(100);
	
	private int peso;
	
	Modelo (int peso){this.peso = peso;}	
	
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

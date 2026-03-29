package model;

public class Carta {
	
	
	private Naipe naipe; 
	private Modelo modelo;
	private int peso;
	
	
	public int getPeso () {
		return this.peso;
	}
	
	public void setPeso (int  peso) {
		this.peso = peso;
	}

	public Modelo getModelo() {
		return modelo;
	}

	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}

	public Naipe getNaipe() {
		return naipe;
	}

	public void setNaipe(Naipe naipe) {
		this.naipe = naipe;
	}
	
	public void setNaipePorValor(int valorInput) {
        this.naipe = Naipe.fromPeso(valorInput);
    }
	
    public void setModeloPorPeso(int peso) {
        this.modelo = Modelo.fromPeso(peso);
    }
    
    @Override
    public String toString() {
        return "Carta: " + this.modelo + " de " + this.naipe;
    }
	
}

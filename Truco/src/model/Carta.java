package model;

public class Carta {
	
	private int id;
	private Naipe naipe; 
	private Modelo modelo;
		
	
	public int getId () {
		return this.id;
	}
	
	public void setId (int id) {
		this.id = id;
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

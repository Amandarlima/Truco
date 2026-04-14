package rules;

import java.util.ArrayList;
import java.util.List;

import model.Carta;
import model.Modelo;

public class Regra {

	//comparar cartas
	public void comparar_cartas(List<Carta> monte) {
		
	}
	
	public List<Carta> comparar_filtrar_cartas(List<Carta> listaOriginal) {
	    List<Carta> listaVencedoras = new ArrayList<>();
	    int maiorPeso = -1; 

	    for (Carta cartaAtual : listaOriginal) {
	        // Caso 1: Encontrou uma carta maior que o recorde atual
	        if (cartaAtual.getPeso() > maiorPeso) {
	            maiorPeso = cartaAtual.getPeso();
	            listaVencedoras.clear();    // DESCARTA todas as anteriores menores
	            listaVencedoras.add(cartaAtual); // Mantém apenas a nova "mais alta"
	        } 
	        // Caso 2: Empate (mesmo peso da maior atual)
	        else if (cartaAtual.getPeso() == maiorPeso) {
	            listaVencedoras.add(cartaAtual); // Adiciona junto às outras
	        }
	        
	        // Caso 3: Se for menor, o código simplesmente ignora (descarta)
	    }
	    
	    return listaVencedoras;
	}
	
	public Carta encontrar_maior_naipe(List<Carta> cartasNaMesa) {
	    if (cartasNaMesa == null || cartasNaMesa.isEmpty()) {
	        return null;
	    }

	    // Começamos assumindo que a primeira carta tem o naipe mais forte
	    Carta maiorCarta = cartasNaMesa.get(0);

	    for (int i = 1; i < cartasNaMesa.size(); i++) {
	        Carta cartaAtual = cartasNaMesa.get(i);

	        // Compara apenas o peso do enum Naipe
	        if (cartaAtual.getNaipe().getPeso() > maiorCarta.getNaipe().getPeso()) {
	            maiorCarta = cartaAtual;
	        }
	    }

	    return maiorCarta;
	}

	//comparar manilhas

	public List<Carta> filtrar_manilhas(List<Carta> cartasNaMesa, Carta cartaVira) {
	    List<Carta> manilhasEncontradas = new ArrayList<>();
	    
	    // Extraímos o modelo do objeto cartaVira recebido no parâmetro
	    Modelo modeloDoVira = cartaVira.getModelo();
	    Modelo modeloManilha;

	    // Lógica do ciclo baseada no modelo extraído
	    if (modeloDoVira == Modelo.TRES) {
	        modeloManilha = Modelo.QUATRO;
	    } else {
	        int proximoIndice = modeloDoVira.ordinal() + 1;
	        modeloManilha = Modelo.values()[proximoIndice];
	    }

	    // Percorre a lista de cartas da mesa verificando o modelo de cada uma
	    for (Carta carta : cartasNaMesa) {
	        if (carta.getModelo() == modeloManilha) {
	            manilhasEncontradas.add(carta);
	        }
	    }

	    return manilhasEncontradas;
	}
	
	
	
	//verificar vitoria

	public List<Integer> verificar_vitoria(List<Carta> cartasNaMesa, Carta cartaVira) {
		List<Integer> idsVencedores = new ArrayList<>();

		// 1. Tenta filtrar as manilhas na mesa
		List<Carta> manilhas = filtrar_manilhas(cartasNaMesa, cartaVira);

		if (!manilhas.isEmpty()) {
			// Caso existam manilhas, a de maior naipe vence
			Carta vencedora = encontrar_maior_naipe(manilhas);
			if (vencedora != null) {
				idsVencedores.add(vencedora.getId());
			}
		} else {
			// 2. Caso não existam manilhas, verifica as cartas comuns de maior peso
			List<Carta> vencedorasComuns = comparar_filtrar_cartas(cartasNaMesa);

			for (Carta c : vencedorasComuns) {
				int id = c.getId();
				// Adiciona o ID se ele ainda não estiver na lista (evita repetidos)
				if (!idsVencedores.contains(id)) {
					idsVencedores.add(id);
				}
			}
		}

		return idsVencedores;
	}
}
	//pedir truco

	//mao de onze vantagem
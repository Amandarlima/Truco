package rules;

import java.util.ArrayList;
import java.util.List;

import model.Carta;
import model.Modelo;
import model.Rodada;

public class Regra {

	// comparar cartas
	public void comparar_cartas(List<Carta> monte) {

	}

	public List<Carta> comparar_filtrar_cartas(List<Carta> listaOriginal) {
		List<Carta> listaVencedoras = new ArrayList<>();
		int maiorPeso = -1;

		for (Carta cartaAtual : listaOriginal) {

			int pesoAtual = cartaAtual.getModelo().getPeso();
			// Caso 1: Encontrou uma carta maior que o recorde atual
			if (pesoAtual > maiorPeso) {
				maiorPeso = pesoAtual;
				listaVencedoras.clear();
				listaVencedoras.add(cartaAtual);
			} else if (pesoAtual == maiorPeso) {
				listaVencedoras.add(cartaAtual);
			}

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

	// comparar manilhas

	public List<Carta> filtrar_manilhas(List<Carta> cartasNaMesa, Carta cartaVira) {

		List<Carta> manilhasEncontradas = new ArrayList<>();

		if (cartaVira == null) {
			return manilhasEncontradas;
		}

		Modelo modeloDoVira = cartaVira.getModelo();
		Modelo modeloManilha;

		if (modeloDoVira == Modelo.TRES) {
			modeloManilha = Modelo.QUATRO;
		} else {
			int proximoIndice = modeloDoVira.ordinal() + 1;
			modeloManilha = Modelo.values()[proximoIndice];
		}

		for (Carta carta : cartasNaMesa) {
			if (carta.getModelo() == modeloManilha) {
				manilhasEncontradas.add(carta);
			}
		}

		return manilhasEncontradas;
	}

	// verificar vitoria

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

	public Modelo calcularManilha(Carta cartaVira) {

		if (cartaVira == null)
			return null;

		Modelo modeloDoVira = cartaVira.getModelo();

		if (modeloDoVira == Modelo.TRES) {
			return Modelo.QUATRO;
		}

		int proximoIndice = modeloDoVira.ordinal() + 1;
		return Modelo.values()[proximoIndice];
	}

	public boolean pedirTruco(Rodada rodada) {

		int valorAtual = rodada.getValor();

		if (valorAtual >= 12) {
			return false;
		}

		if (valorAtual == 1) {
			rodada.setValor(3);
		} else if (valorAtual == 3) {
			rodada.setValor(6);
		} else if (valorAtual == 6) {
			rodada.setValor(9);
		} else if (valorAtual == 9) {
			rodada.setValor(12);
		}

		return true;
	}

	public boolean podePedirTruco(Rodada rodada) {
		return rodada.getValor() < 12;
	}
	
	public boolean responderTruco(String resposta) {

	    if (resposta == null) return false;

	    resposta = resposta.trim().toLowerCase();

	    return resposta.equals("sim") || resposta.equals("s");
	}
}

// mao de onze vantagem
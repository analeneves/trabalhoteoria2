/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ana Letícia e Valney
 */

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;

public class Automato {
	// Verifica qual estado eh o inicial
	private int estadoInicial(JTable TabelaA) {
		int i;
		for (i = 0; i < TabelaA.getRowCount(); i++) {
			if ("Sim".equals(TabelaA.getValueAt(i, 1))) {
				return converteInteiro((String) TabelaA.getValueAt(i, 0));
			}
		}
		return -1;
	}

	private Boolean palavraVazia(JTable TabelaA, String palavra, JLabel labelResultado) {
		if (palavra.length() == 0) {
			if (!"".equals(TabelaA.getValueAt(0, 4))) {
				labelResultado.setText("Palavra NAO ACEITA!");
				return true;
			}
		}
		if (palavra.length() == 1) {
			if (palavra.equals(TabelaA.getValueAt(0, 4)) && estadoFinal(0, TabelaA)) {
				labelResultado.setText("Palavra ACEITA!");
				return true;
			}
		}
		return false;
	}

	// Funcao inicial para vericiar uma palavra digitada
	public void VerificaPalavra(JTable TabelaA, String Palavra, JLabel labelResultado) {
		int inicial = estadoInicial(TabelaA);
		if (inicial == -1) {
			JOptionPane.showMessageDialog(null, "Nenhum estado inicial definido");
			return;
		}
		char[] auxPalavra;
		auxPalavra = Palavra.toCharArray();
		Pilha p1 = new Pilha();
		labelResultado.setText("Palavra NAO ACEITA!");
		if (!palavraVazia(TabelaA, Palavra, labelResultado)) {
			BuscaEstado(TabelaA, inicial, auxPalavra, 0, p1, labelResultado);
		}
		labelResultado.setVisible(true);
	}

	// Verifica se a conexao eh vazia
	private Boolean conexaoVazia(JTable TabelaA, int estadoAtual) {
		return "Nao".equals(TabelaA.getValueAt(estadoAtual, 2)) && "".equals(TabelaA.getValueAt(estadoAtual, 4))
				&& "".equals(TabelaA.getValueAt(estadoAtual, 5)) && "".equals(TabelaA.getValueAt(estadoAtual, 6));
	}

	// Converte uma String para Inteiro
	private int converteInteiro(String estadoAtual) {
		if (!"".equals(estadoAtual)) {
			return Integer.parseInt(estadoAtual);
		} else {
			return -1;
		}
	}

	// Converte uma String para Char
	private char converteChar(String estadoAtual) {
		if (!"".equals(estadoAtual)) {
			return estadoAtual.charAt(0);
		} else {
			return ' ';
		}
	}

	// Verifica se um determinado estado eh um estado final
	private Boolean estadoFinal(int estadoAtual, JTable TabelaA) {
		int i;
		for (i = 0; i < TabelaA.getRowCount(); i++) {
			if (Integer.toString(estadoAtual).equals(TabelaA.getValueAt(i, 0))) {
				if ("Sim".equals(TabelaA.getValueAt(i, 2))) {
					return true;
				}
			}
		}
		return false;
	}

	// Funcao recursiva para realizar a busca no automato
	private void BuscaEstado(JTable TabelaA, int estadoAtual, char[] palavra, int indicePalavra, Pilha p1,
			JLabel labelResultado) {
		int i;
		for (i = 0; i < TabelaA.getRowCount(); i++) {
			if (Integer.toString(estadoAtual).equals(TabelaA.getValueAt(i, 0))) {
				// Se caso tiver uma conexao vazia
				if (conexaoVazia(TabelaA, i) && !"".equals(TabelaA.getValueAt(i, 3))) {
					// Salvando log
					/*
					 * log = log + Integer.toString(estadoAtual) + " ---> " +
					 * (String) TabelaA.getValueAt(i, 3) + "\n";
					 */
					// Esvaziando Pilha
					if ("Sim".equals(TabelaA.getValueAt(i, 1)))
						p1.esvaziarPilha();
					BuscaEstado(TabelaA, converteInteiro((String) TabelaA.getValueAt(i, 3)), palavra, indicePalavra, p1,
							labelResultado);
				} else {
					// Se caso o valor lido na fita estiver no estado
					if (!"".equals(TabelaA.getValueAt(i, 4)) && indicePalavra <= palavra.length - 1) {
						if (palavra[indicePalavra] == converteChar((String) TabelaA.getValueAt(i, 4))) {
							// Desempilha
							if (!"".equals(TabelaA.getValueAt(i, 5))) {
								if (!p1.estaVazia()) {
									p1.desempilha();
								} else {
									return;
								}
							}
							// Empilha
							if (!"".equals(TabelaA.getValueAt(i, 6)))
								p1.empilha(converteChar((String) TabelaA.getValueAt(i, 6)));
							// // Salvando log
							// log = log + Integer.toString(estadoAtual) + "
							// ---> " + (String) TabelaA.getValueAt(i, 3)
							// + "\n";
							// Esvaziando Pilha
							if ("Sim".equals(TabelaA.getValueAt(i, 1)))
								p1.esvaziarPilha();
							// Proximo Estado
							if (!"".equals(TabelaA.getValueAt(i, 3)) && indicePalavra <= palavra.length - 1) {
								BuscaEstado(TabelaA, converteInteiro((String) TabelaA.getValueAt(i, 3)), palavra,
										++indicePalavra, p1, labelResultado);
								indicePalavra--;
							}
						}
					}
				}
			}
		}
		// Criterio de parada
		if (palavra.length == indicePalavra && estadoFinal(estadoAtual, TabelaA) && p1.estaVazia()) {

			labelResultado.setText("Palavra ACEITA!");
		}
	}
}

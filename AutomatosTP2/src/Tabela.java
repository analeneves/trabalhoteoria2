/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ana Letícia e Valney
 */

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Tabela {
	// Adicona um estado na tabela
	public void AdicionaTabela(String Estado, String Conexao, String leitura, String Desempilha, String Empilha,
			String Inicial, String Final, JTable TabelaA) {
		DefaultTableModel dtmTabelA = (DefaultTableModel) TabelaA.getModel();
		Object[] dados = { Estado, Inicial, Final, Conexao, leitura, Desempilha, Empilha };
		dtmTabelA.addRow(dados);
	}

	// Remove todos os exercicios da table
	public void removeTodosTabela(javax.swing.JTable TabelaA) {
		DefaultTableModel dtmExercicios = (DefaultTableModel) TabelaA.getModel();
		dtmExercicios.setNumRows(0);
		TabelaA.clearSelection();
	}

	/*
	 * // Move linha tabela para cima public void linhaParaCima(JTable TabelaA)
	 * { if (TabelaA.getSelectedRow() == -1) {
	 * JOptionPane.showMessageDialog(null, "Selecione um conexao para mover");
	 * return; }
	 * 
	 * int index = TabelaA.getSelectedRow(); DefaultTableModel model =
	 * (DefaultTableModel) TabelaA.getModel();
	 * 
	 * if (index > 0) { model.moveRow(index, index, index - 1);
	 * TabelaA.setRowSelectionInterval(index - 1, index - 1); } }
	 */

	/*
	 * // Move liha tabela para baixo public void linhaParaBaixo(JTable TabelaA)
	 * { if (TabelaA.getSelectedRow() == -1) {
	 * JOptionPane.showMessageDialog(null, "Selecione um conexao para mover");
	 * return; }
	 * 
	 * int index = TabelaA.getSelectedRow(); DefaultTableModel model =
	 * (DefaultTableModel) TabelaA.getModel();
	 * 
	 * if (index < model.getRowCount() - 1) { model.moveRow(index, index, index
	 * + 1); TabelaA.setRowSelectionInterval(index + 1, index + 1); } }
	 */

	// Transforma o conteudo da tabela em string
	public String tabelaToString(JTable TabelaA) {
		String exercicios = " ";
		int i, nLinhas = TabelaA.getRowCount();
		for (i = nLinhas - 1; i >= 0; i--) {
			exercicios = (String) TabelaA.getValueAt(i, 0) + "," + // Quebra de
																	// coluna
					(String) TabelaA.getValueAt(i, 1) + "," + (String) TabelaA.getValueAt(i, 2) + ","
					+ (String) TabelaA.getValueAt(i, 3) + "," + (String) TabelaA.getValueAt(i, 4) + ","
					+ (String) TabelaA.getValueAt(i, 5) + "," + (String) TabelaA.getValueAt(i, 6) + "," + "_"
					+ exercicios; // Quebra de linha
		}
		return exercicios;
	}

	// Da String para a tabela
	public void stringToTabela(JTable TabelaA, String automato) {
		int i;
		// String com o exercicios quebrados em "_"
		String[] automatoL = automato.split("_");
		// Modelo Tabela
		DefaultTableModel dtmExercicios = (DefaultTableModel) TabelaA.getModel();
		for (i = 0; i < automatoL.length - 1; i++) {
			String[] coluna = automatoL[i].split(",");
			switch (coluna.length) {
			case 1: {
				Object[] dados = { coluna[0], "", "", "", "", "", "" };
				dtmExercicios.addRow(dados);
				break;
			}
			case 2: {
				Object[] dados = { coluna[0], coluna[1], "", "", "", "", "" };
				dtmExercicios.addRow(dados);
				break;
			}
			case 3: {
				Object[] dados = { coluna[0], coluna[1], coluna[2], "", "", "", "" };
				dtmExercicios.addRow(dados);
				break;
			}
			case 4: {
				Object[] dados = { coluna[0], coluna[1], coluna[2], coluna[3], "", "", "" };
				dtmExercicios.addRow(dados);
				break;
			}
			case 5: {
				Object[] dados = { coluna[0], coluna[1], coluna[2], coluna[3], coluna[4], "", "" };
				dtmExercicios.addRow(dados);
				break;
			}
			case 6: {
				Object[] dados = { coluna[0], coluna[1], coluna[2], coluna[3], coluna[4], coluna[5], "" };
				dtmExercicios.addRow(dados);
				break;
			}
			case 7: {
				Object[] dados = { coluna[0], coluna[1], coluna[2], coluna[3], coluna[4], coluna[5], coluna[6] };
				dtmExercicios.addRow(dados);
				break;
			}
			default:
				break;
			}

		}
	}
}

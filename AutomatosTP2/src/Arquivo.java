/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ana Letícia e Valney
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
    
public class Arquivo {
	// Descobrindo o local do arquivo a ser salvo
	public void caminhoSalvarArquivo(JTable TabelaA, String Texto, int i) {
		if (i == 1) {
			Tabela tb = new Tabela();
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Escolha o local para salvar o automato!");
			fileChooser.changeToParentDirectory();
			String caminho_padrao = System.getProperty("user.dir");
			File pathInicial = new File(caminho_padrao);
			fileChooser.setCurrentDirectory(pathInicial);
			int retorno = fileChooser.showSaveDialog(fileChooser);
			if (retorno == 0) {
				File file = fileChooser.getSelectedFile();
				salvarAutomato(file.getPath() + ".txt", tb.tabelaToString(TabelaA));
				JOptionPane.showMessageDialog(null, "Arquivo salvo com sucesso!");
			} else {
				JOptionPane.showMessageDialog(null, "Cancelado!");
			}
		} else {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Escolha o local para salvar o automato!");
			String caminho_padrao = System.getProperty("user.dir");
			File pathInicial = new File(caminho_padrao);
			fileChooser.setCurrentDirectory(pathInicial);
			int retorno = fileChooser.showSaveDialog(fileChooser);
			if (retorno == 0) {
				File file = fileChooser.getSelectedFile();
				salvarAutomato(file.getPath() + ".txt", Texto);
				JOptionPane.showMessageDialog(null, "Arquivo salvo com sucesso!");
			} else {
				JOptionPane.showMessageDialog(null, "Cancelado!");
			}
		}
	}

	// Buscando o arquivo de entrada para o automato
	public void caminhoAbrirArquivo(JTable TabelaA) {
		Tabela tb = new Tabela();
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Escolha o arquivo de texto com o automato!");
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("*.txt", "txt"));
		String caminho_padrao = System.getProperty("user.dir");
		File pathInicial = new File(caminho_padrao);
		fileChooser.setCurrentDirectory(pathInicial);
		int retorno = fileChooser.showOpenDialog(fileChooser);
		if (retorno == 0) {
			File file = fileChooser.getSelectedFile();
			String conteudo = leituraAutomato(file.getPath());
			System.out.println(file.getPath());
			if (conteudo.contains(",") && conteudo.contains("_") && file.getPath().contains("txt")) {
				tb.stringToTabela(TabelaA, conteudo);
			} else {
				JOptionPane.showMessageDialog(null, "Arquivo Invalido!");
			}
		}
	}

	// Leitura do arquivo
	private String leituraAutomato(String Caminho) {
		String conteudo = "";
		try {
			FileReader arq = new FileReader(Caminho);
			BufferedReader AnaLe = new BufferedReader(
					new InputStreamReader(new FileInputStream(Caminho), StandardCharsets.UTF_8));
			String linha;
			try {
				linha = AnaLe.readLine();
				while (linha != null) {
					conteudo += linha;
					linha = AnaLe.readLine();
				}
				arq.close();
				AnaLe.close();
				return conteudo;
			} catch (IOException ex) {
				System.out.println("ERRO! Nao foi possivel ler o arquivo!");
				return "";
			}

		} catch (FileNotFoundException ex) {
			System.out.println("ERRO! Arquivo nao encontrado!");
			return "";
		}
	}

	// Salvar automato em um arquivo
	public void salvarAutomato(String Caminho, String Texto) {
		try {
			FileWriter arq = new FileWriter(Caminho);
			try (PrintWriter gravarArq = new PrintWriter(arq)) {
				gravarArq.println(Texto);
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}

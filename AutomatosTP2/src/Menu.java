/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ana Letícia e Valney
 */

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import javax.swing.*;

public class Menu extends javax.swing.JFrame {
	private static final long serialVersionUID = 1L;

	public Menu() {
		initComponents();
		// Definindo cor de fundo
		this.getContentPane().setBackground(new java.awt.Color(255, 255, 255));
		// Definindo cor fundo tabela
		TabelaScroll.getViewport().setBackground(new java.awt.Color(255, 255, 255));
		// Iniciando maximizado
		setExtendedState(MAXIMIZED_BOTH);
		// Deixando o label resultado invisivel
		labelResultado.setVisible(false);
	}

	// Adiciona uma conexao a tabela
	private void adicionaEstadoTabela() {
		String Estado = textBoxEstado.getText();
		String Conexao = textBoxConexao.getText();
		String Leitura = textBoxLeitura.getText();
		String Desempilha = textBoxDesempilha.getText();
		String Empilha = textBoxEmpilha.getText();
		String Inicial;
		String Final;

		if (radioInicialSim.isSelected()) {
			Inicial = "Sim";
		} else {
			Inicial = "Nao";
		}
		if (radioFinalSim.isSelected()) {
			Final = "Sim";
		} else {
			Final = "Nao";
		}

		Tabela tb = new Tabela();
		tb.AdicionaTabela(Estado, Conexao, Leitura, Desempilha, Empilha, Inicial, Final, TabelaA);
	}

	// Remove todas as informacoes da tabela
	private void removeTodoConteudo(int op) {
		if (op == 1) {
			int pergunta = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir todo conteudo?");
			if (pergunta == 0) {
				Tabela tb = new Tabela();
				tb.removeTodosTabela(TabelaA);
				textBoxEstado.setText("");
				textBoxConexao.setText("");
				textBoxLeitura.setText("");
				textBoxDesempilha.setText("");
				textBoxEmpilha.setText("");
				textBoxPalavra.setText("");
				textAreaLog.setText("");
				radioInicialNao.setSelected(true);
				radioFinalNao.setSelected(true);
				labelResultado.setVisible(false);
			}
		} else {
			Tabela tb = new Tabela();
			tb.removeTodosTabela(TabelaA);
			textBoxEstado.setText("");
			textBoxConexao.setText("");
			textBoxLeitura.setText("");
			textBoxDesempilha.setText("");
			textBoxEmpilha.setText("");
			textBoxPalavra.setText("");
			textAreaLog.setText("");
			radioInicialNao.setSelected(true);
			radioFinalNao.setSelected(true);
			labelResultado.setVisible(false);
		}
	}

	// Busca uma palavra no automato de pilha
	private void verificaPalavra() {
		Automato bsc = new Automato();
		textAreaLog.setText("");
		bsc.VerificaPalavra(TabelaA, textBoxPalavra.getText(), labelResultado);
	}

	// Controle para ter apenas um estado inicial
	private Boolean verificaEstadoInicial(String Estado) {
		int i, estadoInicial = 0;
		if (radioInicialSim.isSelected()) {
			for (i = 0; i < TabelaA.getRowCount(); i++) {
				if ("Sim".equals(TabelaA.getValueAt(i, 1))) {
					estadoInicial = 1;
					if (Estado.equals(TabelaA.getValueAt(i, 0))) {
						return true;
					}
				}
			}
		}
		if (estadoInicial == 0) {
			return true;
		} else {
			JOptionPane.showMessageDialog(null, "So pode existir um estado inicial!");
			return false;
		}
	}

	// Verifica campo estado
	private Boolean verificaCampos() {
		if ("".equals(textBoxEstado.getText())) {
			JOptionPane.showMessageDialog(null, "Digite o valor do estado!");
			return false;
		}
		// Verifica se ja tem um campo igual
		int i;
		String Estado = textBoxEstado.getText();
		String Conexao = textBoxConexao.getText();
		String Leitura = textBoxLeitura.getText();
		String Desempilha = textBoxDesempilha.getText();
		String Empilha = textBoxEmpilha.getText();
		String Inicial;
		String Final;
		if (radioInicialSim.isSelected()) {
			Inicial = "Sim";
		} else {
			Inicial = "Nao";
		}
		if (radioFinalSim.isSelected()) {
			Final = "Sim";
		} else {
			Final = "Nao";
		}
		for (i = 0; i < TabelaA.getRowCount(); i++) {
			if ("".equals(Desempilha) && "".equals(Empilha)) {
				if (Estado.equals(TabelaA.getValueAt(i, 0)) && Inicial.equals(TabelaA.getValueAt(i, 1))
						&& Final.equals(TabelaA.getValueAt(i, 2)) && Conexao.equals(TabelaA.getValueAt(i, 3))
						&& Leitura.equals(TabelaA.getValueAt(i, 4)) && Desempilha.equals(TabelaA.getValueAt(i, 5))
						&& Empilha.equals(TabelaA.getValueAt(i, 6))) {
					JOptionPane.showMessageDialog(null, " Esses dados ja existem no automato de pilha!");
					return false;
				}
			}
			if (Estado.equals(TabelaA.getValueAt(i, 0))) {
				String message;
				if (!Inicial.equals(TabelaA.getValueAt(i, 1))) {
					message = "O estado inicial desse estado ja foi definido anteriormente. Remova-o para modificar!";
					JOptionPane.showMessageDialog(null, message);
					return false;
				} else if (!Final.equals(TabelaA.getValueAt(i, 2))) {
					message = "O estado final desse estado ja foi definido anteriormente. Remova-o para modificar!";
					JOptionPane.showMessageDialog(null, message);
					return false;
				}
			}
		}
		return true;
	}

	// Limpar Campos
	private void limparCampos() {
		textBoxEstado.setText("");
		textBoxConexao.setText("");
		textBoxLeitura.setText("");
		textBoxDesempilha.setText("");
		textBoxEmpilha.setText("");
		radioInicialNao.setSelected(true);
		radioFinalNao.setSelected(true);
	}

	// Informacoes do botao AJUDA
	private void ajuda() {
		String message = "- Use apenas numeros para representar estados.\n ";
		message += "- Para representar o movimento vazio, deixe os campos de leitura, desempilhar e empilhar em branco.\n";
		message += "- Qualquer simbolo pode ser empilhado e desempilhado.\n";
		JOptionPane.showMessageDialog(null, message);
	}

	// Informacoes do botao SOBRE
	private void sobre() {
		String message = "- Curso de Ciencia da Computacao - UFSJ.\n " + "- Professor: Vinicius H. S. Durelli.\n";
		message += "- Alunos: Ana Leticia e Valney Faria.\n";
		message += "- Trabalho Baseado na Versao feita por: Gustavo Detomi, Gustavo Silva e Felipe Faria.\n";
		JOptionPane.showMessageDialog(null, message);
	}

	// Salvar o automato
	private void salvar() {
		if (TabelaA.getRowCount() > 0) {
			Arquivo diretorio = new Arquivo();
			diretorio.caminhoSalvarArquivo(TabelaA, "", 1);
		} else {
			JOptionPane.showMessageDialog(null, "Crie um automato para ser salvo!");
		}
	}

	// Abrir o automato
	private void abrir() {
		if (TabelaA.getRowCount() > 0) {
			int pergunta = JOptionPane.showConfirmDialog(null,
					"Tem certeza que deseja abrir um novo arquivo? Todo o conteudo atual sera excluido!");
			if (pergunta == 0) {
				removeTodoConteudo(0);
				Arquivo diretorio = new Arquivo();
				diretorio.caminhoAbrirArquivo(TabelaA);
			}
		} else {
			removeTodoConteudo(0);
			Arquivo diretorio = new Arquivo();
			diretorio.caminhoAbrirArquivo(TabelaA);
		}
	}

	public void copia(String texto) {
		Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
		StringSelection ss = new StringSelection(texto);
		clip.setContents(ss, ss);
	}

	private void initComponents() {
		grupoRadioInicial = new javax.swing.ButtonGroup();
		grupoRadioFinal = new javax.swing.ButtonGroup();
		TabelaScroll = new javax.swing.JScrollPane();
		TabelaA = new javax.swing.JTable();
		Adicionar = new javax.swing.JPanel();
		labelTitulo = new javax.swing.JLabel();
		labelEstado = new javax.swing.JLabel();
		labelConexao = new javax.swing.JLabel();
		labelLeitura = new javax.swing.JLabel();
		labelDesempilha = new javax.swing.JLabel();
		labelEmpilha = new javax.swing.JLabel();
		labelInicial = new javax.swing.JLabel();
		labelFinal = new javax.swing.JLabel();
		textBoxEstado = new javax.swing.JTextField();
		textBoxConexao = new javax.swing.JTextField();
		textBoxLeitura = new javax.swing.JTextField();
		textBoxDesempilha = new javax.swing.JTextField();
		textBoxEmpilha = new javax.swing.JTextField();
		radioInicialSim = new javax.swing.JRadioButton();
		radioInicialNao = new javax.swing.JRadioButton();
		radioFinalSim = new javax.swing.JRadioButton();
		radioFinalNao = new javax.swing.JRadioButton();
		btAdicionar = new javax.swing.JButton();
		btsTabela = new javax.swing.JPanel();
		testePalavra = new javax.swing.JPanel();
		labelTituloPalavra = new javax.swing.JLabel();
		labelPalavra = new javax.swing.JLabel();
		textBoxPalavra = new javax.swing.JTextField();
		btTestar = new javax.swing.JButton();
		labelResultado = new javax.swing.JLabel();
		btAbrirArquivo = new javax.swing.JButton();
		btSalvarArquivo = new javax.swing.JButton();
		btSobre = new javax.swing.JButton();
		btAjuda = new javax.swing.JButton();
		jLabel1 = new javax.swing.JLabel();
		jPanel1 = new javax.swing.JPanel();
		textAreaLog = new javax.swing.JTextArea();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("Montador e Simulador de Automatos de Pilha");
		setMinimumSize(new java.awt.Dimension(1140, 630));
		setPreferredSize(new java.awt.Dimension(1170, 670));
		setResizable(true);

		TabelaScroll.setBackground(new java.awt.Color(255, 255, 255));

		// Cor de fundo da tabela
		TabelaA.setBackground(new java.awt.Color(89, 130, 215));
		TabelaA.setFont(new java.awt.Font("Times New Roman", 1, 14));
		TabelaA.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {

		}, new String[] { "Estado", "Inicial", "Final", "Prox. Estado", "Le da Fita", "Desempilha", "Empilha" }) {
			private static final long serialVersionUID = 1L;
			boolean[] canEdit = new boolean[] { false, false, false, false, false, false, false };

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});

		TabelaA.setGridColor(new java.awt.Color(255, 255, 255));
		TabelaA.setOpaque(false);
		TabelaA.setSelectionBackground(new java.awt.Color(178, 255, 100));
		TabelaA.setSelectionForeground(new java.awt.Color(0, 0, 0));
		TabelaA.getTableHeader().setReorderingAllowed(false);
		TabelaScroll.setViewportView(TabelaA);

		Adicionar.setBackground(new java.awt.Color(255, 255, 255));
		Adicionar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
		Adicionar.setInheritsPopupMenu(true);

		labelTitulo.setFont(new java.awt.Font("Tahoma", 1, 18));
		labelTitulo.setText("Adicionar Estados:");

		labelEstado.setFont(new java.awt.Font("Tahoma", 0, 14));
		labelEstado.setText("Nome do Estado");

		labelConexao.setFont(new java.awt.Font("Tahoma", 0, 14));
		labelConexao.setText("Proximo Estado");

		labelLeitura.setFont(new java.awt.Font("Tahoma", 0, 14));
		labelLeitura.setText("Simbolo Lido da Fila");

		labelDesempilha.setFont(new java.awt.Font("Tahoma", 0, 14));
		labelDesempilha.setText("Desempilhar Simbolo");

		labelEmpilha.setFont(new java.awt.Font("Tahoma", 0, 14));
		labelEmpilha.setText("Empilhar Simbolo");

		labelInicial.setFont(new java.awt.Font("Tahoma", 0, 14));
		labelInicial.setText("Inicial");

		labelFinal.setFont(new java.awt.Font("Tahoma", 0, 14));
		labelFinal.setText("Final");

		textBoxEstado.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent evt) {
				textBoxEstadoKeyTyped(evt);
			}
		});

		textBoxConexao.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent evt) {
				textBoxConexaoKeyTyped(evt);
			}
		});

		textBoxLeitura.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent evt) {
				textBoxLeituraKeyTyped(evt);
			}
		});

		textBoxDesempilha.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent evt) {
				textBoxDesempilhaKeyTyped(evt);
			}
		});

		textBoxEmpilha.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent evt) {
				textBoxEmpilhaKeyTyped(evt);
			}
		});

		grupoRadioInicial.add(radioInicialSim);
		radioInicialSim.setSelected(true);
		radioInicialSim.setText("Sim");
		radioInicialSim.setToolTipText("");

		grupoRadioInicial.add(radioInicialNao);
		radioInicialNao.setText("Nao");
		radioInicialNao.setToolTipText("");

		grupoRadioFinal.add(radioFinalSim);
		radioFinalSim.setText("Sim");
		radioFinalSim.setToolTipText("");

		grupoRadioFinal.add(radioFinalNao);
		radioFinalNao.setSelected(true);
		radioFinalNao.setText("Nao");
		radioFinalNao.setToolTipText("");

		btAdicionar.setBackground(new java.awt.Color(118, 164, 255));
		btAdicionar.setText("Adicionar Estado");
		btAdicionar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btAdicionarActionPerformed();
			}
		});

		// Trechos de codigo gerados pelo NetBeans para construcao das
		// interfaces
		javax.swing.GroupLayout AdicionarLayout = new javax.swing.GroupLayout(Adicionar);
		Adicionar.setLayout(AdicionarLayout);
		AdicionarLayout
				.setHorizontalGroup(
						AdicionarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(AdicionarLayout
										.createSequentialGroup().addContainerGap().addGroup(AdicionarLayout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(labelTitulo).addGroup(AdicionarLayout
														.createSequentialGroup().addGroup(AdicionarLayout
																.createParallelGroup(
																		javax.swing.GroupLayout.Alignment.LEADING)
																.addGroup(AdicionarLayout
																		.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING)
																		.addGroup(AdicionarLayout
																				.createSequentialGroup()
																				.addGap(14, 14, 14)
																				.addGroup(AdicionarLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(labelLeitura,
																								javax.swing.GroupLayout.Alignment.TRAILING)
																						.addComponent(labelConexao,
																								javax.swing.GroupLayout.Alignment.TRAILING)
																						.addComponent(labelEstado,
																								javax.swing.GroupLayout.Alignment.TRAILING)
																						.addComponent(labelEmpilha,
																								javax.swing.GroupLayout.Alignment.TRAILING)))
																		.addComponent(labelInicial,
																				javax.swing.GroupLayout.Alignment.TRAILING)
																		.addComponent(
																				labelFinal,
																				javax.swing.GroupLayout.Alignment.TRAILING))
																.addComponent(labelDesempilha))
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addGroup(AdicionarLayout
																.createParallelGroup(
																		javax.swing.GroupLayout.Alignment.LEADING,
																		false)
																.addGroup(AdicionarLayout.createSequentialGroup()
																		.addComponent(radioInicialSim)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																		.addComponent(radioInicialNao))
																.addGroup(AdicionarLayout.createSequentialGroup()
																		.addComponent(radioFinalSim)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																		.addComponent(radioFinalNao))
																.addComponent(btAdicionar,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE)
																.addComponent(textBoxConexao)
																.addComponent(textBoxEstado)
																.addComponent(textBoxLeitura)
																.addComponent(textBoxDesempilha)
																.addComponent(textBoxEmpilha))))
										.addGap(0, 0, Short.MAX_VALUE)));
		AdicionarLayout.setVerticalGroup(AdicionarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(AdicionarLayout.createSequentialGroup().addContainerGap().addComponent(labelTitulo)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(AdicionarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(labelEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 20,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(textBoxEstado, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGap(3, 3, 3)
						.addGroup(AdicionarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(labelConexao, javax.swing.GroupLayout.PREFERRED_SIZE, 20,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(textBoxConexao, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(AdicionarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(labelLeitura, javax.swing.GroupLayout.PREFERRED_SIZE, 19,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(textBoxLeitura, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGap(4, 4, 4)
						.addGroup(AdicionarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(labelDesempilha, javax.swing.GroupLayout.PREFERRED_SIZE, 20,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(textBoxDesempilha, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGap(3, 3, 3)
						.addGroup(AdicionarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(labelEmpilha, javax.swing.GroupLayout.PREFERRED_SIZE, 20,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(textBoxEmpilha, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGap(3, 3, 3)
						.addGroup(AdicionarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(labelInicial, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
								.addGroup(
										AdicionarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(radioInicialSim).addComponent(radioInicialNao)))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(AdicionarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(labelFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(radioFinalSim).addComponent(radioFinalNao))
						.addGap(5, 5, 5).addComponent(btAdicionar).addContainerGap()));

		javax.swing.GroupLayout btsTabelaLayout = new javax.swing.GroupLayout(btsTabela);
		btsTabela.setLayout(btsTabelaLayout);

		testePalavra.setBackground(new java.awt.Color(255, 255, 255));

		labelTituloPalavra.setFont(new java.awt.Font("Tahoma", 1, 18));
		labelTituloPalavra.setText("Testar Palavra:");

		labelPalavra.setFont(new java.awt.Font("Tahoma", 0, 14));
		labelPalavra.setText("Fita:");

		btTestar.setBackground(new java.awt.Color(118, 164, 255, 215));
		btTestar.setFont(new java.awt.Font("Tahoma", 1, 18));
		btTestar.setText("TESTAR");
		btTestar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btTestarActionPerformed();
			}
		});

		labelResultado.setFont(new java.awt.Font("Tahoma", 1, 14));
		labelResultado.setText("Resultado:");

		btAbrirArquivo.setBackground(new java.awt.Color(118, 164, 255, 215));
		btAbrirArquivo.setText("Abrir Automato");
		btAbrirArquivo.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btAbrirArquivoActionPerformed();
			}
		});

		btSalvarArquivo.setBackground(new java.awt.Color(118, 164, 255, 215));
		btSalvarArquivo.setText("Salvar Automato");
		btSalvarArquivo.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btSalvarArquivoActionPerformed();
			}
		});

		btSobre.setBackground(new java.awt.Color(118, 164, 255, 215));
		btSobre.setText("SOBRE");
		btSobre.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btSobreActionPerformed();
			}
		});

		btAjuda.setBackground(new java.awt.Color(118, 164, 255, 215));
		btAjuda.setText("AJUDA");
		btAjuda.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btAjudaActionPerformed();
			}
		});

		javax.swing.GroupLayout testePalavraLayout = new javax.swing.GroupLayout(testePalavra);
		testePalavra.setLayout(testePalavraLayout);
		testePalavraLayout.setHorizontalGroup(testePalavraLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(testePalavraLayout.createSequentialGroup().addContainerGap()
						.addGroup(testePalavraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
								.addComponent(btAjuda, javax.swing.GroupLayout.PREFERRED_SIZE, 161,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(btSobre, javax.swing.GroupLayout.PREFERRED_SIZE, 161,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(btSalvarArquivo, javax.swing.GroupLayout.PREFERRED_SIZE, 161,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(btAbrirArquivo, javax.swing.GroupLayout.PREFERRED_SIZE, 161,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(labelResultado, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btTestar, javax.swing.GroupLayout.PREFERRED_SIZE, 161,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(labelTituloPalavra)
								.addGroup(testePalavraLayout.createSequentialGroup().addComponent(labelPalavra)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(textBoxPalavra, javax.swing.GroupLayout.PREFERRED_SIZE, 131,
												javax.swing.GroupLayout.PREFERRED_SIZE)))
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		testePalavraLayout
				.setVerticalGroup(
						testePalavraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(
										testePalavraLayout.createSequentialGroup().addContainerGap()
												.addComponent(labelTituloPalavra)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addGroup(testePalavraLayout
														.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING,
																false)
														.addComponent(textBoxPalavra).addComponent(labelPalavra,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(btTestar)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(labelResultado)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(btAbrirArquivo)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(btSalvarArquivo)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(btSobre)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(btAjuda).addContainerGap(
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap(42, Short.MAX_VALUE)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
								.addComponent(Adicionar, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(testePalavra, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGap(24, 24, 24)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(TabelaScroll)
								.addComponent(btsTabela, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup().addGap(20, 20, 20).addComponent(jLabel1))
								.addGroup(layout.createSequentialGroup().addGap(18, 18, 18).addComponent(jPanel1,
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)))
						.addContainerGap(34, Short.MAX_VALUE)));
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								layout.createSequentialGroup().addGap(28, 28, 28).addGroup(layout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(
												javax.swing.GroupLayout.Alignment.TRAILING,
												layout.createSequentialGroup()
														.addComponent(Adicionar, javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addGap(18, 18, 18).addComponent(testePalavra,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
										.addGroup(layout.createSequentialGroup().addGroup(layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(layout.createSequentialGroup()
														.addComponent(TabelaScroll,
																javax.swing.GroupLayout.PREFERRED_SIZE, 516,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addComponent(btsTabela, javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
												.addGroup(layout.createSequentialGroup().addComponent(jLabel1)
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)))
												.addGap(0, 25, Short.MAX_VALUE)))));

		pack();
	}

	private void btTestarActionPerformed() {
		verificaPalavra();
	}

	private void btAjudaActionPerformed() {
		ajuda();
	}

	private void btSobreActionPerformed() {
		sobre();
	}

	private void textBoxEstadoKeyTyped(java.awt.event.KeyEvent evt) {
		// Bloqueio de caracteres digitados
		String caracteres = "0987654321";
		if (!caracteres.contains(evt.getKeyChar() + "")) {
			evt.consume();
		}
	}

	private void textBoxConexaoKeyTyped(java.awt.event.KeyEvent evt) {
		// Bloqueio de caracteres digitados
		String caracteres = "0987654321";
		if (!caracteres.contains(evt.getKeyChar() + "")) {
			evt.consume();
		}
	}

	private void btAdicionarActionPerformed() {
		if (verificaCampos() && verificaEstadoInicial(textBoxEstado.getText())) {
			adicionaEstadoTabela();
			limparCampos();
		}
	}

	private void textBoxLeituraKeyTyped(java.awt.event.KeyEvent evt) {
		// Bloqueia o numero de caracteres digitados
		String l = textBoxLeitura.getText();
		int tamanho = l.length();
		if (tamanho > 0) {
			evt.consume();
		}
	}

	private void textBoxDesempilhaKeyTyped(java.awt.event.KeyEvent evt) {
		// Bloqueio numero de caracteres digitados
		String l = textBoxDesempilha.getText();
		int tamanho = l.length();
		if (tamanho > 0) {
			evt.consume();
		}
	}

	private void textBoxEmpilhaKeyTyped(java.awt.event.KeyEvent evt) {
		String l = textBoxEmpilha.getText();
		int tamanho = l.length();
		if (tamanho > 0) {
			evt.consume();
		}
	}

	private void btSalvarArquivoActionPerformed() {
		salvar();
	}

	private void btAbrirArquivoActionPerformed() {
		abrir();
	}

	public static void main(String[] args) {
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Menu().setVisible(true);
			}
		});
	}

	// Declaracao de Variaveis
	private javax.swing.JPanel Adicionar;
	private javax.swing.JTable TabelaA;
	private javax.swing.JScrollPane TabelaScroll;
	private javax.swing.JButton btAbrirArquivo;
	private javax.swing.JButton btAdicionar;
	private javax.swing.JButton btAjuda;
	private javax.swing.JButton btSalvarArquivo;
	private javax.swing.JButton btSobre;
	private javax.swing.JButton btTestar;
	private javax.swing.JPanel btsTabela;
	private javax.swing.ButtonGroup grupoRadioFinal;
	private javax.swing.ButtonGroup grupoRadioInicial;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JLabel labelConexao;
	private javax.swing.JLabel labelDesempilha;
	private javax.swing.JLabel labelEmpilha;
	private javax.swing.JLabel labelEstado;
	private javax.swing.JLabel labelFinal;
	private javax.swing.JLabel labelInicial;
	private javax.swing.JLabel labelLeitura;
	private javax.swing.JLabel labelPalavra;
	private javax.swing.JLabel labelResultado;
	private javax.swing.JLabel labelTitulo;
	private javax.swing.JLabel labelTituloPalavra;
	private javax.swing.JRadioButton radioFinalNao;
	private javax.swing.JRadioButton radioFinalSim;
	private javax.swing.JRadioButton radioInicialNao;
	private javax.swing.JRadioButton radioInicialSim;
	private javax.swing.JPanel testePalavra;
	private javax.swing.JTextArea textAreaLog;
	private javax.swing.JTextField textBoxConexao;
	private javax.swing.JTextField textBoxDesempilha;
	private javax.swing.JTextField textBoxEmpilha;
	private javax.swing.JTextField textBoxEstado;
	private javax.swing.JTextField textBoxLeitura;
	private javax.swing.JTextField textBoxPalavra;
}

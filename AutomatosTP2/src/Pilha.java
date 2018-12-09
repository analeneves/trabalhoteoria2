/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ana Letícia e Valney
 */
public class Pilha {
	private String pilha = "";

	public void empilha(char p) {
		this.pilha = pilha + p;
	}

	public void desempilha() {
		if (!this.pilha.isEmpty()) {
			this.pilha = this.pilha.substring(0, this.pilha.length() - 1);
		}
	}

	public void imprimePilha() {
		System.out.println(this.pilha);
	}

	public boolean estaVazia() {
		return this.pilha.isEmpty();
	}

	public int tamanhoPilha() {
		return this.pilha.length();
	}

	public void esvaziarPilha() {
		this.pilha = "";
	}
}

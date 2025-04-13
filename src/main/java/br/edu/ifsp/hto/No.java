package br.edu.ifsp.hto;

public class No {
    Contato contato;
    No esquerdo;
    No direito;

    public No(Contato contato) {
        this.contato = contato;
        this.esquerdo = null;
        this.direito = null;
    }
}
package br.edu.ifsp.hto;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ArvoreBinariaBusca {
    private static final int ESPACO_IMPRESSAO = 4;
    No raiz;

    public ArvoreBinariaBusca() {
        this.raiz = null;
    }

    public void inserir(Contato novoContato) {
        if (estaVazia()) {
            raiz = new No(novoContato);
            return;
        }

        No atual = raiz;
        No pai = null;

        while (atual != null) {
            pai = atual;
            if (novoContato.codigo < atual.contato.codigo) {
                atual = atual.esquerdo;
            } else if (novoContato.codigo > atual.contato.codigo) {
                atual = atual.direito;
            } else {
                return;
            }
        }

        No novoNo = new No(novoContato);
        if (novoContato.codigo < pai.contato.codigo) {
            pai.esquerdo = novoNo;
        } else {
            pai.direito = novoNo;
        }
    }

    public No buscar(int codigoBuscado) {
        No atual = raiz;
        while (atual != null) {
            if (codigoBuscado == atual.contato.codigo)
                return atual;
            else if (codigoBuscado < atual.contato.codigo)
                atual = atual.esquerdo;
            else
                atual = atual.direito;
        }
        return null;
    }

    public boolean existe(int codigo) {
        return (buscar(codigo) != null);
    }

    public void remover(int codigoRemover) {
        if (estaVazia()) {
            return;
        }

        No atual = raiz;
        No pai = null;

        while (atual != null && atual.contato.codigo != codigoRemover) {
            pai = atual;
            if (codigoRemover < atual.contato.codigo) {
                atual = atual.esquerdo;
            } else {
                atual = atual.direito;
            }
        }

        if (atual == null) {
            return;
        }

        if (atual.esquerdo == null || atual.direito == null) {
            No filho;
            if (atual.esquerdo != null) {
                filho = atual.esquerdo;
            } else {
                filho = atual.direito;
            }

            if (pai == null) {
                raiz = filho;
            } else {
                if (atual == pai.esquerdo) {
                    pai.esquerdo = filho;
                } else {
                    pai.direito = filho;
                }
            }
        } else {
            No paiSucessor = atual;
            No sucessor = atual.direito;

            while (sucessor.esquerdo != null) {
                paiSucessor = sucessor;
                sucessor = sucessor.esquerdo;
            }

            atual.contato = sucessor.contato;

            if (paiSucessor == atual) {
                paiSucessor.direito = sucessor.direito;
            } else {
                paiSucessor.esquerdo = sucessor.direito;
            }
        }
    }

    public boolean estaVazia() {
        return raiz == null;
    }

    public int contarNos() {
        return contarNosRecursivo(raiz);
    }

    private int contarNosRecursivo(No atual) {
        if (atual == null) {
            return 0;
        }
        return 1 + contarNosRecursivo(atual.esquerdo) + contarNosRecursivo(atual.direito);
    }

    public int contarNosFolha() {
        return contarNosFolhaRecursivo(raiz);
    }

    private int contarNosFolhaRecursivo(No atual) {
        if (atual == null) {
            return 0;
        }
        if (atual.esquerdo == null && atual.direito == null) {
            return 1;
        }
        return contarNosFolhaRecursivo(atual.esquerdo) + contarNosFolhaRecursivo(atual.direito);
    }

    public int calcularAlturaArvore() {
        return calcularAlturaRecursivo(raiz);
    }

    private int calcularAlturaRecursivo(No atual) {
        if (atual == null) {
            return -1;
        }
        int alturaEsquerda = calcularAlturaRecursivo(atual.esquerdo);
        int alturaDireita = calcularAlturaRecursivo(atual.direito);
        return 1 + Math.max(alturaEsquerda, alturaDireita);
    }

    public String listarContatosEmOrdemAlfabetica() {
        List<Contato> contatos = new ArrayList<>();
        coletarContatos(raiz, contatos);

        contatos.sort(Comparator.comparing(contato -> contato.nome));

        StringBuilder sb = new StringBuilder();
        for (Contato contato : contatos) {
            sb.append(contato.toString()).append("\n");
        }
        return sb.toString();
    }

    private void coletarContatos(No no, List<Contato> contatos) {
        if (no != null) {
            coletarContatos(no.esquerdo, contatos);
            contatos.add(no.contato);
            coletarContatos(no.direito, contatos);
        }
    }
}
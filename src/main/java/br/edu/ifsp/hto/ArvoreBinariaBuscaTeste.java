package br.edu.ifsp.hto;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ArvoreBinariaBuscaTeste {
    private ArvoreBinariaBusca arvore;

    @Before
    public void configurar() {
        arvore = new ArvoreBinariaBusca();
    }

    @Test
    public void testarInserirContato() {
        Contato contato = new Contato(10, "João", "11111111111");
        arvore.inserir(contato);

        assertFalse(arvore.estaVazia());
        assertNotNull(arvore.buscar(10));
        assertEquals(contato, arvore.buscar(10).contato);
    }

    @Test
    public void testarBuscarContatoExistente() {
        Contato contato1 = new Contato(5, "Maria", "22222222222");
        Contato contato2 = new Contato(15, "José", "33333333333");

        arvore.inserir(contato1);
        arvore.inserir(contato2);

        No encontrado = arvore.buscar(15);
        assertNotNull(encontrado);
        assertEquals("José", encontrado.contato.nome);
    }

    @Test
    public void testarBuscarContatoInexistente() {
        arvore.inserir(new Contato(20, "Ana", "44444444444"));
        assertNull(arvore.buscar(10));
    }

    @Test
    public void testarExisteContato() {
        arvore.inserir(new Contato(7, "Carlos", "55555555555"));
        assertTrue(arvore.existe(7));
        assertFalse(arvore.existe(99));
    }

    @Test
    public void testarRemoverContato() {
        Contato contato1 = new Contato(10, "A", "11111111111");
        Contato contato2 = new Contato(5, "B", "22222222222");
        Contato contato3 = new Contato(15, "C", "33333333333");

        arvore.inserir(contato1);
        arvore.inserir(contato2);
        arvore.inserir(contato3);

        arvore.remover(5);
        assertFalse(arvore.existe(5));
        assertEquals(2, arvore.contarNos());
    }

    @Test
    public void testarContarNosFolha() {
        assertEquals(0, arvore.contarNosFolha());

        arvore.inserir(new Contato(50, "Raiz", "11111111111"));
        assertEquals(1, arvore.contarNosFolha());

        arvore.inserir(new Contato(30, "Esq", "22222222222"));
        arvore.inserir(new Contato(70, "Dir", "33333333333"));
        assertEquals(2, arvore.contarNosFolha());

        arvore.inserir(new Contato(20, "EsqEsq", "44444444444"));
        assertEquals(2, arvore.contarNosFolha());
    }

    @Test
    public void testarListarContatosEmOrdem() {
        arvore.inserir(new Contato(3, "C", "11111111111"));
        arvore.inserir(new Contato(1, "A", "22222222222"));
        arvore.inserir(new Contato(2, "B", "33333333333"));
        arvore.inserir(new Contato(4, "D", "44444444444"));

        String esperado = "Código: 1, Nome: A, Telefone: 22222222222\n" +
                "Código: 2, Nome: B, Telefone: 33333333333\n" +
                "Código: 3, Nome: C, Telefone: 11111111111\n" +
                "Código: 4, Nome: D, Telefone: 44444444444\n";

        assertEquals(esperado, arvore.listarContatosEmOrdemAlfabetica());
    }

    @Test
    public void testarListarContatosArvoreVazia() {
        assertEquals("", arvore.listarContatosEmOrdemAlfabetica());
    }

    @Test
    public void testarInserirContatosComCodigosIguais() {
        Contato contato1 = new Contato(10, "Primeiro", "11111111111");
        Contato contato2 = new Contato(10, "Segundo", "22222222222");

        arvore.inserir(contato1);
        arvore.inserir(contato2);

        assertEquals(1, arvore.contarNos());
        assertEquals("Primeiro", arvore.buscar(10).contato.nome);
    }
}
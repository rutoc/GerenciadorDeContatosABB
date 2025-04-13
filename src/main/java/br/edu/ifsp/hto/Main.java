package br.edu.ifsp.hto;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    private ArvoreBinariaBusca arvoreContatos;
    private Scanner scanner;

    public Main() {
        arvoreContatos = new ArvoreBinariaBusca();
        scanner = new Scanner(System.in);
    }

    public void executar() {
        int opcao;
        do {
            exibirMenu();
            opcao = scanner.nextInt();
            scanner.nextLine();

            long inicio = System.nanoTime();

            switch (opcao) {
                case 1:
                    adicionarContato();
                    break;
                case 2:
                    pesquisarContato();
                    break;
                case 3:
                    listarContatos();
                    break;
                case 4:
                    removerContato();
                    break;
                case 5:
                    importarCSV();
                    break;
                case 6:
                    exibirEstatisticas();
                    break;
                case 0:
                    System.out.println("Saindo do sistema...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }

            long fim = System.nanoTime();
            System.out.println("Tempo de execução: " + (fim - inicio) + " nanosegundos");

        } while (opcao != 0);
    }

    private void exibirMenu() {
        System.out.println("\n=== Sistema de Gerenciamento de Contatos ===");
        System.out.println("1. Adicionar Contato");
        System.out.println("2. Pesquisar Contato por Código");
        System.out.println("3. Listar Contatos em Ordem Alfabética");
        System.out.println("4. Remover Contato por Código");
        System.out.println("5. Importar Contatos de CSV");
        System.out.println("6. Exibir Estatísticas do Sistema");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private void adicionarContato() {
        System.out.println("\n--- Adicionar Contato ---");

        int codigo;
        do {
            System.out.print("Código (número positivo único): ");
            codigo = scanner.nextInt();
            scanner.nextLine();

            if (codigo <= 0) {
                System.out.println("O código deve ser um número positivo!");
            } else if (arvoreContatos.existe(codigo)) {
                System.out.println("Já existe um contato com este código!");
            } else {
                break;
            }
        } while (true);

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();

        Contato novoContato = new Contato(codigo, nome, telefone);
        arvoreContatos.inserir(novoContato);

        System.out.println("Contato adicionado com sucesso!");
    }

    private void pesquisarContato() {
        System.out.println("\n--- Pesquisar Contato ---");
        System.out.print("Digite o código do contato: ");
        int codigo = scanner.nextInt();
        scanner.nextLine();

        No no = arvoreContatos.buscar(codigo);
        if (no != null) {
            System.out.println("Contato encontrado:");
            System.out.println(no.contato);
        } else {
            System.out.println("Contato não encontrado!");
        }
    }

    private void listarContatos() {
        System.out.println("\n--- Lista de Contatos em Ordem Alfabética ---");
        if (arvoreContatos.estaVazia()) {
            System.out.println("Nenhum contato cadastrado.");
        } else {
            System.out.println(arvoreContatos.listarContatosEmOrdemAlfabetica());
        }
    }

    private void removerContato() {
        System.out.println("\n--- Remover Contato ---");
        System.out.print("Digite o código do contato a ser removido: ");
        int codigo = scanner.nextInt();
        scanner.nextLine();

        if (arvoreContatos.existe(codigo)) {
            arvoreContatos.remover(codigo);
            System.out.println("Contato removido com sucesso!");
        } else {
            System.out.println("Contato não encontrado!");
        }
    }

    private void importarCSV() {
        System.out.println("\n--- Importar Contatos de CSV ---");
        System.out.print("Digite o caminho do arquivo CSV: ");
        String caminhoArquivo = scanner.nextLine();

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            int contador = 0;

            br.readLine();

            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length == 3) {
                    int codigo = Integer.parseInt(dados[0].trim());
                    String nome = dados[1].trim();
                    String telefone = dados[2].trim();

                    if (!arvoreContatos.existe(codigo)) {
                        Contato contato = new Contato(codigo, nome, telefone);
                        arvoreContatos.inserir(contato);
                        contador++;
                    }
                }
            }

            System.out.println("Importação concluída com sucesso!");
            System.out.println(contador + " contatos importados.");

        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Erro no formato dos dados: " + e.getMessage());
        }
    }

    private void exibirEstatisticas() {
        System.out.println("\n--- Estatísticas do Sistema ---");
        System.out.println("Total de contatos cadastrados: " + arvoreContatos.contarNos());
        System.out.println("Altura da árvore: " + arvoreContatos.calcularAlturaArvore());
        System.out.println("Número de nós folha: " + arvoreContatos.contarNosFolha());
    }

    public static void main(String[] args) {
        Main sistema = new Main();
        sistema.executar();
    }
}
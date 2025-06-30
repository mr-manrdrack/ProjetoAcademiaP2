package br.upe.projetoAcademiaP2.ui;

import java.util.Scanner;

public class InterfaceAdm {
    private final Scanner sc;

    public InterfaceAdm(Scanner scanner) {
        this.sc = scanner;
    }

    public void exibirMenu() {
        boolean sair = false;

        while (!sair) {
            System.out.println("=".repeat(20));
            System.out.println("MENU ADMINISTRADOR");
            System.out.println("=".repeat(20));
            System.out.println("1 - Cadastrar novo aluno");
            System.out.println("2 - Listar alunos");
            System.out.println("3 - Excluir aluno");
            System.out.println("4 - Sair");
            System.out.print("Escolha uma opção: ");

            try {
                int opcao = sc.nextInt();
                sc.nextLine();

                switch (opcao) {
                    case 1:
                        cadastrarUsuario();
                        break;
                    case 2:

                        break;
                    case 3:

                        break;
                    case 4:

                        sair = true;
                        break;
                    default:
                        System.out.println("Opção inválida! Tente novamente.");
                }

            } catch (Exception e) {
                System.out.println("Erro: Entrada inválida!");
                sc.nextLine();
            }
        }
    }

    private void cadastrarUsuario() {
        System.out.println("\n--- Cadastro de Novo Usuário ---");
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("Login: ");
        String login = sc.nextLine();
        System.out.print("Senha: ");
        String senha = sc.nextLine();

        System.out.println("Usuário '" + nome + "' cadastrado.");
    }
}
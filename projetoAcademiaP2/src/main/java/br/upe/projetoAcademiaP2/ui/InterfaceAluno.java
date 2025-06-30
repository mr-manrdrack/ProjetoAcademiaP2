package br.upe.projetoAcademiaP2.ui;

import java.util.Scanner;

public class InterfaceAluno {
    private final Scanner sc;

    public InterfaceAluno(Scanner scanner) {
        this.sc = scanner;
    }

    public void exibirMenu() {
        boolean sair = false;

        while (!sair) {
            System.out.println("=".repeat(20));
            System.out.println("MENU DO ALUNO");
            System.out.println("=".repeat(20));
            System.out.println("1 - Exercícios");
            System.out.println("2 - Indicadores");
            System.out.println("3 - Plano de treino");
            System.out.println("4 - Relatório");
            System.out.println("5 - Sair");
            System.out.print("Escolha uma opção: ");

            try {
                int opcao = sc.nextInt();
                sc.nextLine();

                switch (opcao) {
                    case 1:

                        break;
                    case 2:

                        break;
                    case 3:

                        break;
                    case 4:

                        break;
                    case 5:
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
}
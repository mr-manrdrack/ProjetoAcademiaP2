package br.upe.projetoAcademiaP2.ui;

import java.util.Scanner;

public class Relatorios {
    private final Scanner sc = new Scanner(System.in);

    public void exibirMenuRelatorios(){
        boolean sair = false;

        while (sair){
            System.out.println("=".repeat(20));
            System.out.println("RELATÓRIOS");
            System.out.println("=".repeat(20));
            System.out.println("1 - Relátorio geral");
            System.out.println("2 - Relatório comparativo");
            System.out.println("3 - Sair");
            System.out.print("Escolha uma opção: ");

            try {
                int opcao = sc.nextInt();
                sc.nextLine();

                switch (opcao){
                    case 1:
                        relatorioGeral();
                        break;
                    case 2:
                        relatorioComparativo();
                        break;
                    case 3:
                        System.out.println("Saindo...");
                        sair = true;
                        break;
                    default:
                        System.out.println("Opção inválida! Tente novamente");
                }
            }catch (Exception e){
                System.out.println("Erro: Entrada inválida!");
                sc.nextLine();
            }
        }
    }

    private void relatorioComparativo() {
    }

    private void relatorioGeral() {
    }
}

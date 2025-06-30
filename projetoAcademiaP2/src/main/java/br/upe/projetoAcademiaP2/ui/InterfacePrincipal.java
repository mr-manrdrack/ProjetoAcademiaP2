package br.upe.projetoAcademiaP2.ui;


import java.util.InputMismatchException;
import java.util.Scanner;

public class InterfacePrincipal {

    private final Scanner sc;

    public InterfacePrincipal(Scanner scGlobal) {
        this.sc = scGlobal;
    }

    public void exibirMenuPrincipal() {
        boolean sair = true;
        while (!sair){
            System.out.println("=".repeat(20));
            System.out.println("BEM-VINDO");
            System.out.println("=".repeat(20));
            System.out.println("1 - Entrar");
            System.out.println("2 - Sair");
            System.out.print("Escolha uma opção: ");

            try{
                int opcao = sc.nextInt();
                sc.nextLine();

                switch (opcao){
                    case 1:
                        realizarLogin();
                        break;
                    case 2:
                        System.out.println("Saindo...");
                        sair = true;
                        break;
                    default:
                        System.out.println("Opção inválida! Tente novamente");
                }
            }catch (InputMismatchException e){
                System.out.println("Erro: Entrada inválida!");
                sc.nextLine();
            }
        }
    }

    private void realizarLogin() {

        System.out.print("Login: ");
        String user = sc.nextLine();

        System.out.print("Senha: ");
        String senha = sc.nextLine();
    }
}
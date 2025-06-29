package br.upe.projetoAcademiaP2.ui;

import java.util.Scanner;

import br.upe.projetoAcademiaP2.business.UsuarioBusiness;

public class App {

    private static Scanner sc = new Scanner(System.in);
    private static UsuarioBusiness usuarioBusiness = new UsuarioBusiness();

    public static void main (String[] args){

        while (true){
            System.out.println("---- SISTEMA ACADEMIA ----");
            System.out.println("1 - Login\n2 - Sair");
            System.out.println("Escolha uma opção: ");

            int opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao){
                case 1:
                    realizarLogin();
                    break;
                case 2:
                    System.out.println("Saindo do sistema.");
                    return;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }

    private static void realizarLogin() {
        System.out.println("E-mail: ");
        String email = sc.nextLine();
        System.out.println("Senha: ");
        String senha = sc.nextLine();

        String tipoUsuario = usuarioBusiness.autenticar(email, senha);

        if (tipoUsuario != null){
            System.out.println("Login realizado com sucesso!");

            switch (tipoUsuario){
                case "ADM":
                    InterfaceAdm interfaceAdm = new InterfaceAdm();
                    interfaceAdm.exibirMenu();
                    break;
                case "ALUNO":
                    InterfaceAluno interfaceAluno = new InterfaceAluno();
                    interfaceAluno.exibirMenu();
                    break;
            }
        }else {
            System.out.println("E-mail ou senha inválidos! Tente novamente.");
        }
    }
}
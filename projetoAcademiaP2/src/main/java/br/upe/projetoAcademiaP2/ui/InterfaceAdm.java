package br.upe.projetoAcademiaP2.ui;

import br.upe.projetoAcademiaP2.data.beans.Usuario;

import java.util.Scanner;

public class InterfaceAdm {
    private final Scanner sc = new Scanner(System.in);
    private final Usuario adm;

    public InterfaceAdm(Usuario adm) {
        this.adm = adm;
    }

    public void exibirMenuAdm() {
        boolean sair = false;
        while (!sair){
            System.out.println("=".repeat(20));
            System.out.println("MENU ADMINISTRADOR");
            System.out.println("=".repeat(20));
            System.out.println("1 - Cadastrar alunos");
            System.out.println("2 - Listar alunos");
            System.out.println("3 - Modificar alunos");
            System.out.println("4 - Excluir alunos");
            System.out.println("5 - Sair");
            System.out.print("Escolha uma opção: ");

            try {
                int opcao = sc.nextInt();
                sc.nextLine();

                switch (opcao){
                    case 1:
                        cadastrarAluno();
                        break;
                    case 2:
                        listarAlunos();
                        break;
                    case 3:
                        modificarAluno();
                        break;
                    case 4:
                        excluirAluno();
                        break;
                    case 5:
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

    private void modificarAluno() {
    }

    private void cadastrarAluno() {
        System.out.println("\n--- Cadastro de Novo Usuário ---");
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("Login: ");
        String login = sc.nextLine();
        System.out.print("Senha: ");
        String senha = sc.nextLine();
    }

    private void excluirAluno() {
    }

    private void listarAlunos() {
    }
}
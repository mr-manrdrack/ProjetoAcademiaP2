package br.upe.projetoAcademiaP2.ui;

import br.upe.projetoAcademiaP2.business.UsuarioBusiness;
import br.upe.projetoAcademiaP2.data.beans.Comum;
import br.upe.projetoAcademiaP2.data.beans.Usuario;
import java.util.InputMismatchException;
import java.util.Scanner;

public class InterfaceAdm {

    private UsuarioBusiness usuarioBusiness;
    private Scanner sc;

    public InterfaceAdm(Usuario Usuario) {
        this.usuarioBusiness = new UsuarioBusiness();
        this.sc = new Scanner(System.in);
    }

    public void exibirMenu() {
        boolean sair = false;
        while (!sair) {
            System.out.println("\n---- MENU ADMINISTRADOR ----");
            System.out.println("1 - Gerenciar Alunos");
            System.out.println("2 - Sair");
            System.out.print("Escolha uma opção: ");

            try {
                int opcao = sc.nextInt();
                sc.nextLine();

                switch (opcao) {
                    case 1:
                        gerenciarAlunos();
                        break;
                    case 2:
                        sair = true;
                        break;
                    default:
                        System.out.println("Opção inválida! Tente novamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Erro: Por favor, digite um número válido.");
                sc.nextLine(); // Limpa o buffer para evitar loop infinito
            }
        }
    }

    public void gerenciarAlunos() {
        boolean sair = false;
        while (!sair) {
            System.out.println("\n---- GERENCIAR ALUNOS ----");
            System.out.println("1 - Cadastrar aluno");
            System.out.println("2 - Listar alunos");
            System.out.println("3 - Atualizar dados do aluno");
            System.out.println("4 - Excluir aluno");
            System.out.println("5 - Voltar ao Menu Anterior");
            System.out.print("Escolha uma opção: ");

            try {
                int opcao = sc.nextInt();
                sc.nextLine();

                switch (opcao) {
                    case 1:
                        cadastrarAluno();
                        break;
                    case 2:
                        System.out.println("Funcionalidade não implementada.");
                        break;
                    case 3:
                        System.out.println("Funcionalidade não implementada.");
                        break;
                    case 4:
                        System.out.println("Funcionalidade não implementada.");
                        break;
                    case 5:
                        sair = true;
                        break;
                    default:
                        System.out.println("Opção inválida! Tente novamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Erro: Por favor, digite um número válido.");
                sc.nextLine();
            }
        }
    }

    private void cadastrarAluno() {
        System.out.println("\n---- CADASTRO DE NOVO ALUNO ----");
        System.out.print("Nome completo: ");
        String nome = sc.nextLine();
        System.out.print("E-mail: ");
        String email = sc.nextLine();
        System.out.print("Senha: ");
        String senha = sc.nextLine();
        System.out.print("Telefone (opcional): ");
        String telefone = sc.nextLine();

        Comum novoAluno = new Comum(nome, telefone, email, senha, null, null, null);

        usuarioBusiness.cadastrarUsuario(novoAluno);
    }
}
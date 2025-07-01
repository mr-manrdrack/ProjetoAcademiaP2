package br.upe.projetoAcademiaP2.ui;

import br.upe.projetoAcademiaP2.business.UsuarioBusiness;
import br.upe.projetoAcademiaP2.data.beans.Comum;
import br.upe.projetoAcademiaP2.data.beans.Usuario;

import java.util.List;
import java.util.Scanner;

public class InterfaceAdm {
    private final Scanner sc = new Scanner(System.in);
    private final Usuario adm;
    private final UsuarioBusiness usuarioBusiness = new UsuarioBusiness();

    public InterfaceAdm(Usuario adm) {
        this.adm = adm;
    }

    public void exibirMenuAdm() {
        boolean sair = false;
        while (!sair) {
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
                int opcao = Integer.parseInt(sc.nextLine());

                switch (opcao) {
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
            } catch (Exception e) {
                System.out.println("Erro: Entrada inválida!");
            }
        }
    }

    private void cadastrarAluno() {
        System.out.println("\n--- Cadastro de Novo Aluno ---");
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Senha: ");
        String senha = sc.nextLine();
        if (usuarioBusiness.listarUsuarios().stream().anyMatch(u -> u.getEmail().equalsIgnoreCase(email))) {
            System.out.println("Erro: Já existe um aluno com esse email.");
            return;
        }

        Usuario novo = new Comum(nome, null, email, senha, null, null, null);
        usuarioBusiness.cadastrarUsuario(novo);
    }

    private void listarAlunos() {
        System.out.println("\n--- Lista de Alunos ---");
        List<Comum> alunos = usuarioBusiness.listarUsuariosComuns();
        if (alunos.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado.");
        } else {
            for (Comum aluno : alunos) {
                System.out.println("Nome: " + aluno.getNome() + " | Email: " + aluno.getEmail());
            }
        }
    }

    private void modificarAluno() {
        System.out.println("\n--- Modificar Aluno ---");
        System.out.print("Digite o email do aluno: ");
        String email = sc.nextLine();

        Usuario existente = usuarioBusiness.listarUsuarios()
                .stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);

        if (existente == null) {
            System.out.println("Aluno não encontrado.");
            return;
        }

        System.out.print("Novo nome (deixe vazio para manter): ");
        String nome = sc.nextLine();
        if (!nome.isBlank()) existente.setNome(nome);

        System.out.print("Nova senha (deixe vazio para manter): ");
        String senha = sc.nextLine();
        if (!senha.isBlank()) existente.setSenha(senha);

        usuarioBusiness.atualizarUsuario(existente);
    }

    private void excluirAluno() {
        System.out.println("\n--- Excluir Aluno ---");
        System.out.print("Digite o email do aluno a ser removido: ");
        String email = sc.nextLine();
        usuarioBusiness.deletarUsuario(email);
    }
}
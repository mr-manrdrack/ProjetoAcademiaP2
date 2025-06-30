package br.upe.projetoAcademiaP2.ui;

import br.upe.projetoAcademiaP2.business.UsuarioBusiness;
import br.upe.projetoAcademiaP2.data.beans.Comum;
import br.upe.projetoAcademiaP2.data.beans.Usuario;
import java.util.InputMismatchException;
import java.util.List;
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
                        listarAlunos();
                        break;
                    case 3:
                        atualizarAluno();
                        break;
                    case 4:
                        deletarAluno();
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

        System.out.print("Peso Atual: ");
        double pesoAtual = lerDouble();

        System.out.print("Altura Atual: ");
        double alturaAtual = lerDouble();

        System.out.print("Percentual de Gordura Atual: ");
        double percGorduraAtual = lerDouble();

        Comum novoAluno = new Comum(nome, telefone, email, senha, pesoAtual, alturaAtual, percGorduraAtual);
        usuarioBusiness.cadastrarUsuario(novoAluno);
    }

    private double lerDouble() {
        while (true) {
            String entrada = sc.nextLine().replace(",", ".");
            try {
                return Double.parseDouble(entrada);
            } catch (NumberFormatException e) {
                System.out.print(" Valor inválido. Tente novamente: ");
            }
        }
    }

    private void listarAlunos() {
        System.out.println("\n---- LISTA DE ALUNOS ----");
        List<Comum> alunos = usuarioBusiness.listarUsuariosComuns();
    
        if (alunos.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado.");
        } else {
            for (Comum aluno : alunos) {
                System.out.println("Nome: " + aluno.getNome());
                System.out.println("Email: " + aluno.getEmail());
                System.out.println("Telefone: " + aluno.getTelefone());
                System.out.println("Peso: " + aluno.getPesoAtual());
                System.out.println("Altura: " + aluno.getAlturaAtual());
                System.out.println("Gordura Corporal: " + aluno.getPercGorduraAtual());
                System.out.println("-------------------------");
            }
        }
    }

    private void atualizarAluno() {
        System.out.println("\n---- ATUALIZAR ALUNO ----");
        System.out.print("E-mail do aluno que deseja atualizar: ");
        String email = sc.nextLine();

        Usuario usuario = usuarioBusiness.listarUsuarios().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email) && u instanceof Comum)
                .findFirst()
                .orElse(null);

        if (usuario == null) {
            System.out.println("Aluno não encontrado.");
        return;
        }

        System.out.println("Aluno encontrado: " + usuario.getNome());

        System.out.print("Novo nome (enter para manter): ");
        String nome = sc.nextLine();
        if (!nome.isEmpty()) usuario.setNome(nome);

        System.out.print("Novo telefone (enter para manter): ");
        String telefone = sc.nextLine();
        if (!telefone.isEmpty()) usuario.setTelefone(telefone);

        System.out.print("Novo peso atual (enter para manter): ");
        String pesoStr = sc.nextLine();
        if (!pesoStr.isEmpty()) usuario.setPesoAtual(Double.parseDouble(pesoStr.replace(",", ".")));

        System.out.print("Nova altura atual (enter para manter): ");
        String alturaStr = sc.nextLine();
        if (!alturaStr.isEmpty()) usuario.setAlturaAtual(Double.parseDouble(alturaStr.replace(",", ".")));

        System.out.print("Novo percentual de gordura (enter para manter): ");
        String gorduraStr = sc.nextLine();
        if (!gorduraStr.isEmpty()) usuario.setPercGorduraAtual(Double.parseDouble(gorduraStr.replace(",", ".")));

        usuarioBusiness.atualizarUsuario(usuario);
    }

    private void deletarAluno() {
        System.out.println("\n---- EXCLUIR ALUNO ----");

        List<Usuario> alunos = usuarioBusiness.listarUsuarios().stream()
                .filter(u -> u instanceof Comum)
                .toList();

        if (alunos.isEmpty()) {
            System.out.println("Nenhum aluno encontrado para exclusão.");
            return;
        }

        System.out.println("Alunos cadastrados:");
        for (int i = 0; i < alunos.size(); i++) {
            Usuario u = alunos.get(i);
            System.out.printf("%d - %s (%s)%n", i + 1, u.getNome(), u.getEmail());
        }

        System.out.print("Digite o número do aluno que deseja excluir (ou 0 para cancelar): ");
        int opcao;
        try {
            opcao = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida.");
            return;
        }

        if (opcao <= 0 || opcao > alunos.size()) {
            System.out.println("Operação cancelada.");
            return;
        }

        Usuario selecionado = alunos.get(opcao - 1);
        System.out.print("Tem certeza que deseja excluir " + selecionado.getNome() + "? (s/n): ");
        String confirmacao = sc.nextLine().trim().toLowerCase();

        if (confirmacao.equals("s")) {
            usuarioBusiness.deletarUsuario(selecionado.getEmail());
        } else {
            System.out.println("Operação cancelada.");
        }
    }

}
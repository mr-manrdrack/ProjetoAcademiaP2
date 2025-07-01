package br.upe.projetoAcademiaP2.ui;

import java.util.List;
import java.util.Scanner;
import br.upe.projetoAcademiaP2.business.ExercicioBusiness;
import br.upe.projetoAcademiaP2.data.beans.Exercicio;

public class Exercicios {
    private final Scanner sc = new Scanner(System.in);
    private final ExercicioBusiness exercicioBusiness = new ExercicioBusiness();

    public void exibirMenuExercicios() {
        boolean sair = false;

        while (!sair) {
            System.out.println("=".repeat(20));
            System.out.println("MENU EXERCÍCIOS");
            System.out.println("=".repeat(20));
            System.out.println("1 - Cadastrar exercício");
            System.out.println("2 - Listar exercícios");
            System.out.println("3 - Excluir exercício");
            System.out.println("4 - Modificar exercício");
            System.out.println("5 - Sair");
            System.out.print("Escolha uma opção: ");

            try {
                int opcao = sc.nextInt();
                sc.nextLine();

                switch (opcao) {
                    case 1:
                        cadastrarNovoExercicio();
                        break;
                    case 2:
                        listarTodosExercicios();
                        break;
                    case 3:
                        excluirExercicio();
                        break;
                    case 4:
                        atualizarExercicioExistente();
                        break;
                    case 5:
                        System.out.println("Saindo...");
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

    private void listarTodosExercicios() {
        System.out.println("\n=== LISTA DE EXERCÍCIOS ===");

        List<Exercicio> exercicios = exercicioBusiness.listarExercicios();

        if (exercicios.isEmpty()) {
            System.out.println("Nenhum exercício cadastrado.");
            return;
        }

        for (int i = 0; i < exercicios.size(); i++) {
            Exercicio ex = exercicios.get(i);
            System.out.println((i + 1) + ". " + ex.getNome());
            System.out.println("Descrição: " + ex.getDescricao());
            if (ex.getCaminhoGif() != null && !ex.getCaminhoGif().trim().isEmpty()) {
                System.out.println("Arquivo GIF: " + ex.getCaminhoGif());
            }
            System.out.println();
        }
    }

    private void cadastrarNovoExercicio() {
        System.out.println("\n=== CADASTRAR EXERCÍCIO FÍSICO ===");

        try {
            System.out.print("Nome do exercício: ");
            String nome = sc.nextLine();

            System.out.print("Descrição detalhada: ");
            String descricao = sc.nextLine();

            System.out.print("Caminho do arquivo GIF: ");
            String caminhoGif = sc.nextLine();

            Exercicio novoExercicio = new Exercicio(nome, descricao, caminhoGif);
            exercicioBusiness.salvar(novoExercicio);
            System.out.println("Exercício cadastrado com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao cadastrar exercício: " + e.getMessage());
        }
    }

    private void atualizarExercicioExistente() {
        System.out.println("\n=== ATUALIZAR EXERCÍCIO ===");

        System.out.print("Digite o nome do exercício a ser atualizado: ");
        String nome = sc.nextLine().trim();

        if (nome.isEmpty()) {
            System.out.println("Nome não pode ser vazio!");
            return;
        }

        Exercicio exercicioExistente = exercicioBusiness.buscarExercicioPorNome(nome);

        if (exercicioExistente == null) {
            System.out.println("Exercício não encontrado: '" + nome + "'");
            return;
        }

        System.out.println("\nDigite os novos dados (deixe vazio para manter o atual):");

        System.out.print("Nova descrição: ");
        String novaDescricao = sc.nextLine().trim();

        System.out.print("Novo caminho do GIF: ");
        String novoCaminhoGif = sc.nextLine().trim();

        boolean houveMudanca = false;

        if (!novaDescricao.isEmpty()) {
            exercicioExistente.setDescricao(novaDescricao);
            houveMudanca = true;
        }

        if (!novoCaminhoGif.isEmpty()) {
            exercicioExistente.setCaminhoGif(novoCaminhoGif);
            houveMudanca = true;
        }

        if (houveMudanca) {
            exercicioBusiness.atualizarExercicio(exercicioExistente);
            System.out.println("Exercício atualizado com sucesso!");
        } else {
            System.out.println("Nenhuma alteração foi feita.");
        }
    }

    private void excluirExercicio() {
        System.out.println("\n=== EXCLUIR EXERCÍCIO ===");

        System.out.print("Digite o nome do exercício a ser excluído: ");
        String nome = sc.nextLine().trim();

        if (nome.isEmpty()) {
            System.out.println("Nome não pode ser vazio.");
            return;
        }

        Exercicio exercicio = exercicioBusiness.buscarExercicioPorNome(nome);

        if (exercicio == null) {
            System.out.println("Exercício não encontrado: " + nome);
            return;
        }

        System.out.println("\nExercício encontrado:");
        System.out.println("Nome: " + exercicio.getNome());
        System.out.println("Descrição: " + exercicio.getDescricao());

        exercicioBusiness.deletarExercicio(nome);
        System.out.println("Exercício excluído com sucesso!");
    }
}

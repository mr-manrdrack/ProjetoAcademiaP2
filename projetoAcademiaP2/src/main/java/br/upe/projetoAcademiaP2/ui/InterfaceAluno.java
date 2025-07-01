package br.upe.projetoAcademiaP2.ui;

import br.upe.projetoAcademiaP2.data.beans.Comum;
import br.upe.projetoAcademiaP2.data.beans.Usuario;

import java.util.Scanner;

public class InterfaceAluno {
    private final Scanner sc = new Scanner(System.in);
    private Usuario aluno;
    private Exercicios exercicios = new Exercicios();
    private InterfaceBiomedico indicadores = new InterfaceBiomedico();
    private PlanosDeTreino planoTreino;
    private Relatorios relatorios = new Relatorios();


    public InterfaceAluno(Usuario aluno) {
        this.aluno = new Comum(aluno.getNome(), aluno.getTelefone(), aluno.getEmail(), aluno.getSenha(), aluno.getPesoAtual(),aluno.getAlturaAtual(),aluno.getPercGorduraAtual());
        this.planoTreino = new PlanosDeTreino (this.aluno);
    }

    public void exibirMenuAlunos() {
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
                        exercicios.exibirMenuExercicios();
                        break;
                    case 2:
                        indicadores.exibirMenuIndicadores(aluno);
                        break;
                    case 3:
                        planoTreino.exibirMenuPlanosDeTreino();
                        break;
                    case 4:
                        relatorios.exibirMenuRelatorios(aluno);
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
}
package br.upe.projetoAcademiaP2.ui;

import br.upe.projetoAcademiaP2.business.*;
import br.upe.projetoAcademiaP2.data.beans.*;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class InterfaceAluno {

    // ALTERADO: Atributos agora são privados para melhor encapsulamento.
    private UsuarioBusiness usuarioBusiness;
    //private ExercicioBusiness exercicioBusiness;
    private PlanoTreinoBusiness planoTreinoBusiness;
    //private IndicadorBiomedicoBusiness indicadorBiomedicoBusiness;
    private SecaoTreinoBusiness secaoTreinoBusiness;
    private Scanner sc;
    private Usuario usuarioLogado;

    public InterfaceAluno(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
        this.usuarioBusiness = new UsuarioBusiness();
        //this.exercicioBusiness = new ExercicioBusiness();
        this.planoTreinoBusiness = new PlanoTreinoBusiness();
        //this.indicadorBiomedicoBusiness = new IndicadorBiomedicoBusiness();
        this.secaoTreinoBusiness = new SecaoTreinoBusiness();
        this.sc = new Scanner(System.in);
    }

    public void exibirMenu() {
        boolean sair = false;
        while (!sair) {
            System.out.println("\n---- BEM-VINDO, " + usuarioLogado.getNome() + " ----");
            System.out.println("1 - Plano de Treino");
            System.out.println("2 - Exercícios");
            System.out.println("3 - Iniciar Seção de Treino");
            System.out.println("4 - Indicadores Biomédicos");
            System.out.println("5 - Relatório de evolução");
            System.out.println("6 - Sair");
            System.out.print("Escolha uma opção: ");
            
            try {
                int opcao = sc.nextInt();
                sc.nextLine();

                switch (opcao) {
                    //case 1: planoTreino(); break;
                    //case 2: exercicios(); break;
                    //case 3: secaoTreino(); break;
                    //case 4: indicadoresBio(); break;
                    //case 5: relatorioEvolucao(); break;
                    case 6: sair = true; break;
                    default: System.out.println("Opção inválida! Tente novamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Erro: Entrada inválida. Por favor, digite um número.");
                sc.nextLine();
            }
        }
    }

}
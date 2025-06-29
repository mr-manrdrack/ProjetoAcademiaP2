package br.upe.projetoAcademiaP2.ui;

import java.util.Scanner;

import br.upe.projetoAcademiaP2.business.UsuarioBusiness;

public class InterfaceAluno {

    protected UsuarioBusiness usuarioBusiness;
    protected ExercicioBusiness exercicioBusiness;
    protected PlanoTreinoBusiness planoTreinoBusiness;
    protected IndicadorBiomedicoBusiness indicadorBiomedicoBusiness;
    protected SecaoTreinoBusiness secaoTreinoBusiness;
    protected Scanner sc;

    public InterfaceAluno(){
        this.usuarioBusiness = new UsuarioBusiness();
        this.exercicioBusiness = new ExercicioBusiness();
        this.planoTreinoBusiness = new PlanoTreinoBusiness();
        this.indicadorBiomedicoBusiness = new IndicadorBiomedicoBusiness();
        this.secaoTreinoBusiness = new SecaoTreinoBusiness();
        this.sc = new Scanner(System.in);
    }

    public void exibirMenu(){
        boolean sair = false;

        while(!sair){
            System.out.println("---- BEM-VINDO ----");
            System.out.println("1 - Plano de Treino\n2 - Exercícios\n3 - Seção de Treino\n4 - Indicadores Biomédicos\n5 - Relatório de evolução\n6 - Sair");
            System.out.println("Escolha uma opção: ");
            int opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao){
                case 1:
                    planoTreino();
                    break;
                case 2:
                    exercicios();
                    break;
                case 3:
                    secaoTreino();
                    break;
                case 4:
                    indicadoresBio();
                    break;
                case 5:
                    relatorioEvolucao();
                    break;
                case 6:
                    sair = true;
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }

    public void planoTreino(){
        System.out.println("1 - Visualizar plano de treino\n2 - Cadastrar Plano de treino\n3 - Modificar plano de treino\n4 - Voltar");
        System.out.println("Escolha uma opção: ");
        int opcao = sc.nextInt();
        sc.nextLine();

        switch (opcao){
            case 1:
                //visualizar plano de treino
                break;
            case 2:
                //cadastrar plano de treino
                break;
            case 3:
                //modificar plano de treino
                break;
            case 4:
                break;
            default:
                System.out.println("Opção inválida! Tente novamente.");
        }
    }
    public void exercicios(){
        System.out.println("1 - Listar exercícios\n2 - Cadastrar exercício\n3 - Voltar");
        System.out.println("Escolha uma opção: ");
        int opcao = sc.nextInt();
        sc.nextLine();

        switch (opcao){
            case 1:
                //listar exercicio
                break;
            case 2:
                //cadastrar exercicio
                break;
            case 3:
                break;
            default:
                System.out.println("Opção inválida! Tente novamente.");
        }
    }
    public void secaoTreino(){
        //cadastrar seção de treino
    }
    public void indicadoresBio(){
        System.out.println("1 - Cadastrar indicadores\n2 - Listar indicadores\n3 - Modificar indicadores\n4 - Voltar");
        System.out.println("Escolha uma opção: ");
        int opcao = sc.nextInt();
        sc.nextLine();

        switch (opcao){
            case 1:
                //cadastrar indicadores
                break;
            case 2:
                //listar indicadores
                break;
            case 3:
                //modificar indicadores
                break;
            case 4:
                break;
            default:
                System.out.println("Opção inválida! Tente novamente.");
        }
    }
    public void relatorioEvolucao(){
        //gerar arquivo/relatório de evolução com os indicadores biomédicos
    }
}
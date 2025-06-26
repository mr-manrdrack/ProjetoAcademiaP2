package br.upe.projetoAcademiaP2.ui;

import java.util.Scanner;

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

            switch (opcao){
                case 1:
                    System.out.println("1 - Visualizar plano de treino\n2 - Cadastrar Plano de treino\n3 - Modificar plano de treino\n4 - Voltar");
                case 2:
                    System.out.println("1 - Listar exercícios\n2 - Cadastrar exercício\n3 - Voltar");
                case 3:
                    //cadastrar seção de treino
                case 4:
                    System.out.println("1 - Cadastrar indicadores\n2 - Listar indicadores\n3 - Listar indicadores\n4 - Voltar");
                case 5:
                    //gerar arquivo/relatório de evolução com os indicadores biomédicos
                case 6:
                    return;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }
}
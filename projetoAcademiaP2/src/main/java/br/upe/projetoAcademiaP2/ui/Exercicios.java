package br.upe.projetoAcademiaP2.ui;

import java.util.Scanner;

public class Exercicios {
    private final Scanner sc = new Scanner(System.in);
    
    public void exibirMenuExercicios(){
        boolean sair = false;
        
        while (!sair){
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
                
                switch (opcao){
                    case 1:
                        cadastrarExercicio();
                        break;
                    case 2:
                        listarExercicios();
                        break;
                    case 3:
                        excluirExercicio();
                        break;
                    case 4:
                        modificarExercicio();
                        break;
                    case 5:
                        System.out.println("Saindo...");
                        sair = true;
                        break;
                    default:
                        System.out.println("Opção inválida! Tente novamente.");
                }
            }catch (Exception e){
                System.out.println("Erro: Entrada inválida!");
                sc.nextLine();
            }
        }
    }

    private void modificarExercicio() {
    }

    private void excluirExercicio() {
    }

    private void listarExercicios() {
    }

    private void cadastrarExercicio() {
    }
}
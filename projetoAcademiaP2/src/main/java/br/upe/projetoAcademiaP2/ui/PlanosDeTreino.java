package br.upe.projetoAcademiaP2.ui;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import br.upe.projetoAcademiaP2.data.beans.PlanoTreino;
import br

public class PlanosDeTreino {
    private final Scanner sc = new Scanner(System.in);

    public void exibirMenuPlanosDeTreino(){
        boolean sair = false;

        while (!sair){
            System.out.println("=".repeat(20));
            System.out.println("PLANO DE TREINO");
            System.out.println("=".repeat(20));
            System.out.println("1 - Cadastrar plano de treino");
            System.out.println("2 - Listar plano de treino");
            System.out.println("3 - Excluir plano de treino");
            System.out.println("4 - Modificar plano de treino");
            System.out.println("5 - Sair");
            System.out.print("Escolha uma opção: ");

            try {
                int opcao = sc.nextInt();
                sc.nextLine();

                switch (opcao){
                    case 1:
                        cadastrarPlano();
                        break;
                    case 2:
                        listarPlano();
                        break;
                    case 3:
                        excluirPlano();
                        break;
                    case 4:
                        modificarPlano();
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

    private void modificarPlano() {
    }

    private void excluirPlano() {
    }

    private void listarPlano() {
    }

    private void cadastrarPlanoTreino() {
        
        System.out.println("\n=== CADASTRAR NOVO PLANO DE TREINO ===");

        try {
            System.out.print("Nome do plano: ");
            String nomePlano = sc.nextLine();

            System.out.print("Data de início (dd/MM/yyyy): ");
            String dataInicioStr = sc.nextLine();

            System.out.print("Data de fim (dd/MM/yyyy): ");
            String dataFimStr = sc.nextLine();

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date dataInicio = sdf.parse(dataInicioStr);
            Date dataFim = sdf.parse(dataFimStr);

            PlanoTreino novoPlano = new PlanoTreino(0, nomePlano, dataInicio, dataFim, usuarioLogado);

            coletarExerciciosParaPlano(novoPlano);

            planoTreinoBusiness.cadastrarPlanoDeTreino(usuarioLogado, novoPlano);

        } catch (ParseException e) {
            System.out.println("Erro: Formato de data inválido. Use dd/MM/yyyy");
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar plano: " + e.getMessage());
        }
    }
}
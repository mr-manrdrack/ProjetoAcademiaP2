package br.upe.projetoAcademiaP2.ui;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

import br.upe.projetoAcademiaP2.data.beans.Exercicio;
import br.upe.projetoAcademiaP2.data.beans.ItemPlanoTreino;
import br.upe.projetoAcademiaP2.data.beans.PlanoTreino;
import br.upe.projetoAcademiaP2.data.beans.SecaoTreino;
import br.upe.projetoAcademiaP2.data.beans.Usuario;
import br.upe.projetoAcademiaP2.business.ExercicioBusiness;
import br.upe.projetoAcademiaP2.business.PlanoTreinoBusiness;
import br.upe.projetoAcademiaP2.business.SecaoTreinoBusiness;
import br.upe.projetoAcademiaP2.business.UsuarioBusiness;

public class PlanosDeTreino {

    private UsuarioBusiness usuarioBusiness;
    private ExercicioBusiness exercicioBusiness;
    private PlanoTreinoBusiness planoTreinoBusiness;
    private SecaoTreinoBusiness secaoTreinoBusiness;
    private Scanner sc;
    private Usuario usuarioLogado;

    public PlanosDeTreino(Usuario usuarioLogado){
        this.usuarioBusiness = new UsuarioBusiness();
        this.exercicioBusiness = new ExercicioBusiness();
        this.planoTreinoBusiness = new PlanoTreinoBusiness();
        this.secaoTreinoBusiness = new SecaoTreinoBusiness();
        this.sc = new Scanner(System.in);
        this.usuarioLogado = usuarioLogado;
    }
/*
this.usuarioLogado = usuarioLogado;
 */
    public void exibirMenuPlanosDeTreino(){
        boolean sair = false;

        while (!sair){
            System.out.println("=".repeat(20));
            System.out.println("PLANO DE TREINO");
            System.out.println("=".repeat(20));
            System.out.println("1 - Cadastrar plano de treino");
            System.out.println("2 - Listar plano de treino");
            System.out.println("3 - Modificar plano de treino");
            System.out.println("4 - Seção Treino ");
            System.out.println("5 - Sair");
            System.out.print("Escolha uma opção: ");

            try {
                int opcao = sc.nextInt();
                sc.nextLine();

                switch (opcao){
                    case 1:
                        cadastrarPlanoTreino();
                        break;
                    case 2:
                        listarPlano();
                        break;
                    case 3:
                        modificarPlano();
                        break;
                    case 4:
                        secaoTreino();
                        sair = true;
                        break;
                    case 5:
                        System.out.println("Saindo...");
                        sair = true;
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
        System.out.println("\n=== MODIFICAR PLANO DE TREINO ===");

        PlanoTreino plano = planoTreinoBusiness.carregarPlanoDoUsuario(usuarioLogado);

        if (plano == null) {
            System.out.println("Você não possui um plano de treino para modificar.");
            return;
        }

        boolean finalizar = false;
        while (!finalizar) {
            System.out.println("\n--- Modificando Plano: " + plano.getNomePlano() + " ---");
            planoTreinoBusiness.exibirPlanoDeTreino(plano); // Mostra o estado atual do plano
            System.out.println("\nOpções de Modificação:");
            System.out.println("1 - Alterar nome do plano");
            System.out.println("2 - Alterar datas");
            System.out.println("3 - Adicionar exercício a uma seção");
            System.out.println("4 - Modificar exercício existente");
            System.out.println("5 - Remover exercício de uma seção");
            System.out.println("6 - Salvar e Finalizar modificações");
            System.out.print("Escolha uma opção: ");

            try {
                int opcao = Integer.parseInt(sc.nextLine());

                switch (opcao) {
                    case 1: alterarNomePlano(plano); break;
                    case 2: alterarDatasPlano(plano); break;
                    case 3: adicionarExercicioAoPlano(plano); break;
                    case 4: modificarExercicioExistente(plano); break;
                    case 5: removerExercicioDoPlano(plano); break;
                    case 6:
                        planoTreinoBusiness.modificarPlanoDeTreino(plano);
                        finalizar = true;
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: Digite um número válido.");
            }
        }
    }

    private void alterarNomePlano(PlanoTreino plano) {
        System.out.print("Novo nome do plano (atual: " + plano.getNomePlano() + "): ");
        String novoNome = sc.nextLine();

        if (!novoNome.trim().isEmpty()) {
            plano.setNomePlano(novoNome);
            System.out.println("Nome alterado com sucesso!");
        } else {
            System.out.println("Nome não pode ser vazio.");
        }
    }

    private void alterarDatasPlano(PlanoTreino plano) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            System.out.print("Nova data de início (dd/MM/yyyy) - atual: " + sdf.format(plano.getInicioPlano()) + ": ");
            String dataInicioStr = sc.nextLine();

            System.out.print("Nova data de fim (dd/MM/yyyy) - atual: " + sdf.format(plano.getFimPlano()) + ": ");
            String dataFimStr = sc.nextLine();

            if (!dataInicioStr.trim().isEmpty() && !dataFimStr.trim().isEmpty()) {
                Date dataInicio = sdf.parse(dataInicioStr);
                Date dataFim = sdf.parse(dataFimStr);

                plano.setInicioPlano(dataInicio);
                plano.setFimPlano(dataFim);

                System.out.println("Datas alteradas com sucesso!");
            } else {
                System.out.println("Operação cancelada - datas não podem ser vazias.");
            }

        } catch (ParseException e) {
            System.out.println("Erro: Formato de data inválido. Use dd/MM/yyyy");
        }
    }

    private void adicionarExercicioAoPlano(PlanoTreino plano) {
        System.out.println("\n=== ADICIONAR EXERCÍCIO ===");
        coletarExerciciosParaPlano(plano);
    }

    private void coletarExerciciosParaPlano(PlanoTreino plano) {
        boolean continuar = true;
        while (continuar) {
            System.out.print("\nNome da Seção (ex: Treino A - Peito e Tríceps): ");
            String nomeSecao = sc.nextLine();
            SecaoTreino secao = plano.getOuCriarSecao(nomeSecao);

            System.out.print("Nome do exercício a adicionar nesta seção: ");
            String nomeExercicio = sc.nextLine();

            Exercicio exercicio = exercicioBusiness.buscarExercicioPorNome(nomeExercicio);

            if (exercicio == null) {
                System.out.println("Exercício não encontrado! Cadastre-o primeiro no menu de exercícios.");
            } else {
                try {
                    System.out.print("Número de séries: ");
                    int series = Integer.parseInt(sc.nextLine());
                    System.out.print("Número de repetições: ");
                    int repeticoes = Integer.parseInt(sc.nextLine());
                    System.out.print("Carga (kg): ");
                    int carga = Integer.parseInt(sc.nextLine());

                    ItemPlanoTreino item = new ItemPlanoTreino(exercicio, series, repeticoes, carga);
                    secao.addItemSecao(item); // Adiciona o item à seção correta
                    System.out.println("'" + nomeExercicio + "' adicionado à seção '" + nomeSecao + "'.");

                } catch (NumberFormatException e) {
                    System.out.println("Erro: Digite valores numéricos válidos para séries, repetições e carga.");
                }
            }
            System.out.print("Deseja adicionar outro exercício a este plano? (s/n): ");
            continuar = sc.nextLine().equalsIgnoreCase("s");
        }
    }
    private void modificarExercicioExistente(PlanoTreino plano) {
        if (plano.getItens().isEmpty()) {
            System.out.println("Este plano não possui exercícios para modificar.");
            return;
        }

        System.out.println("Exercícios no plano:");
        for (int i = 0; i < plano.getItens().size(); i++) {
            ItemPlanoTreino item = plano.getItens().get(i);
            System.out.println((i + 1) + ". " + item.getExercicio().getNome() +
                    " (" + item.getSeries() + "x" + item.getRepeticoes() +
                    ", " + item.getCarga() + "kg)");
        }

        try {
            System.out.print("Escolha o exercício para modificar (número): ");
            int escolha = sc.nextInt() - 1;
            sc.nextLine();

            if (escolha >= 0 && escolha < plano.getItens().size()) {
                ItemPlanoTreino item = plano.getItens().get(escolha);

                System.out.print("Novas séries (atual: " + item.getSeries() + "): ");
                int novasSeries = sc.nextInt();

                System.out.print("Novas repetições (atual: " + item.getRepeticoes() + "): ");
                int novasRepeticoes = sc.nextInt();

                System.out.print("Nova carga (atual: " + item.getCarga() + "kg): ");
                int novaCarga = sc.nextInt();
                sc.nextLine();

                item.setSeries(novasSeries);
                item.setRepeticoes(novasRepeticoes);
                item.setCarga(novaCarga);

                System.out.println("Exercício modificado com sucesso!");

            } else {
                System.out.println("Opção inválida!");
            }
        } catch (InputMismatchException e) {
            System.out.println("Erro: Digite um número válido.");
            sc.nextLine();
        }
    }

    private void removerExercicioDoPlano(PlanoTreino plano) {
        if (plano.getItens().isEmpty()) {
            System.out.println("Este plano não possui exercícios para remover.");
            return;
        }

        System.out.println("Exercícios no plano:");
        for (int i = 0; i < plano.getItens().size(); i++) {
            ItemPlanoTreino item = plano.getItens().get(i);
            System.out.println((i + 1) + ". " + item.getExercicio().getNome());
        }

        try {
            System.out.print("Escolha o exercício para remover (número): ");
            int escolha = sc.nextInt() - 1;
            sc.nextLine();

            if (escolha >= 0 && escolha < plano.getItens().size()) {
                ItemPlanoTreino itemRemovido = plano.getItens().remove(escolha);
                System.out.println("Exercício '" + itemRemovido.getExercicio().getNome() + "' removido com sucesso!");
            } else {
                System.out.println("Opção inválida!");
            }
        } catch (InputMismatchException e) {
            System.out.println("Erro: Digite um número válido.");
            sc.nextLine();
        }
    }
    

    private void listarPlano() {
        PlanoTreino planoVisualizar = planoTreinoBusiness.carregarPlanoDoUsuario(usuarioLogado);

        if (planoVisualizar != null){
            planoTreinoBusiness.exibirPlanoDeTreino(planoVisualizar);
        } else {
            System.out.println("Você ainda não possui um plano de treino cadastrado.");
        }
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

    public void secaoTreino() {
        System.out.println("\n=== SEÇÃO DE TREINO ===");
        
        // 1. Carrega o plano de treino do usuário logado.
        // A lógica de negócio agora suporta apenas um plano, então não há mais lista.
        PlanoTreino plano = planoTreinoBusiness.carregarPlanoDoUsuario(usuarioLogado);

        // 2. Verifica se o usuário de fato tem um plano para treinar.
        if (plano == null || plano.getSecoes().isEmpty()) {
            System.out.println("Você precisa ter um plano de treino com exercícios cadastrados para iniciar uma seção.");
            return;
        }

        // 3. Se o plano existe, inicia a seção de treino.
        iniciarSecaoTreino(plano);
    }

    private void iniciarSecaoTreino(PlanoTreino plano) {
        System.out.println("\n=== INICIANDO SEÇÃO DE TREINO ===");
        System.out.println("Plano: " + plano.getNomePlano());

        secaoTreinoBusiness.iniciarSessao(plano);

        System.out.println("\n1 - Exibir seção como cartão (consulta)");
        System.out.println("2 - Executar treino (preenchimento)");
        System.out.print("Escolha uma opção: ");

        try {
            int opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1:
                    exibirSecaoComoCartao(plano);
                    break;
                case 2:
                    executarTreino(plano);
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } catch (InputMismatchException e) {
            System.out.println("Erro: Digite um número válido.");
            sc.nextLine();
        }
    }

    private void exibirSecaoComoCartao(PlanoTreino plano) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("CARTÃO DE TREINO");
        System.out.println("=".repeat(50));
        System.out.println("Plano: " + plano.getNomePlano());
        System.out.println("Usuário: " + usuarioLogado.getNome());
        System.out.println("Data: " + new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        System.out.println("=".repeat(50));

        for (int i = 0; i < plano.getItens().size(); i++) {
            ItemPlanoTreino item = plano.getItens().get(i);
            System.out.println((i + 1) + ". " + item.getExercicio().getNome());
            System.out.println("Séries: " + item.getSeries());
            System.out.println("Repetições: " + item.getRepeticoes());
            System.out.println("Carga: " + item.getCarga() + " kg");
            System.out.println("Descrição: " + item.getExercicio().getDescricao());
            System.out.println();
        }

        System.out.println("=".repeat(50));
        System.out.println("Observações");
        System.out.println("=".repeat(50));
    }

    private void executarTreino(PlanoTreino plano) {
        System.out.println("\n=== EXECUTANDO TREINO: " + plano.getNomePlano() + " ===");
    
        boolean houveAlteracoesNoPlano = false;

        for (SecaoTreino secao : plano.getSecoes()) {
            System.out.println("\n--- INICIANDO SEÇÃO: " + secao.getNomeTreino() + " ---");
            for (ItemPlanoTreino item : secao.getItensPlano()) {
                System.out.println("\n--- Exercício: " + item.getExercicio().getNome() + " ---");
                System.out.println("Planejado: " + item.getSeries() + " séries x " + item.getRepeticoes() + " repetições com " + item.getCarga() + " kg");

                try {
                    System.out.print("Quantas séries você fez? ");
                    int seriesRealizadas = Integer.parseInt(sc.nextLine());
                    System.out.print("Quantas repetições por série? ");
                    int repeticoesRealizadas = Integer.parseInt(sc.nextLine());
                    System.out.print("Qual carga você usou (kg)? ");
                    int cargaRealizada = Integer.parseInt(sc.nextLine());
                
                    boolean houveDiferenca = (item.getSeries() != seriesRealizadas) || (item.getRepeticoes() != repeticoesRealizadas) || (item.getCarga() != cargaRealizada);

                    if (houveDiferenca) {
                        System.out.print("Performance diferente! Deseja atualizar o plano com os novos parâmetros? (s/n): ");
                        String resposta = sc.nextLine();

                        if (resposta.equalsIgnoreCase("s")) {
                        // ALTERADO: A chamada agora é para o método mais simples do serviço de negócio.
                        // Ele apenas atualiza o objeto em memória.
                            secaoTreinoBusiness.registrarPerformance(item, cargaRealizada, repeticoesRealizadas, seriesRealizadas);
                            houveAlteracoesNoPlano = true; // Marca que o plano foi modificado.
                            System.out.println("Parâmetros do exercício atualizados.");
                        } else {
                            System.out.println("Plano mantido sem alterações para este exercício.");
                        }
                    } else {
                        System.out.println("Performance conforme o planejado!");
                    }
                } catch (NumberFormatException e) {
                System.out.println("Erro: Digite valores numéricos válidos.");
                }
            }
        }
    
    // NOVO: Após o término de todos os exercícios, verifica se precisa salvar.
        if (houveAlteracoesNoPlano) {
            System.out.println("\nSalvando todas as alterações no plano de treino...");
            planoTreinoBusiness.modificarPlanoDeTreino(plano); // Salva o estado final do plano UMA ÚNICA VEZ.
        }

        System.out.println("\nSeção de treino concluída!");
    }
}

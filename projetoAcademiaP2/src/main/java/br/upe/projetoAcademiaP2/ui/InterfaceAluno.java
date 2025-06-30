package br.upe.projetoAcademiaP2.ui;

import br.upe.projetoAcademiaP2.business.*;
import br.upe.projetoAcademiaP2.data.beans.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class InterfaceAluno {

    private UsuarioBusiness usuarioBusiness;
    private ExercicioBusiness exercicioBusiness;
    private PlanoTreinoBusiness planoTreinoBusiness;
    private IndicadorBioBusiness indicadorBiomedicoBusiness;
    private SecaoTreinoBusiness secaoTreinoBusiness;
    private Scanner sc;
    private Usuario usuarioLogado;

    public InterfaceAluno(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
        this.usuarioBusiness = new UsuarioBusiness();
        this.exercicioBusiness = new ExercicioBusiness();
        this.planoTreinoBusiness = new PlanoTreinoBusiness();
        //this.indicadorBiomedicoBusiness = new IndicadorBiomedicoBusiness();
        this.secaoTreinoBusiness = new SecaoTreinoBusiness();
        this.sc = new Scanner(System.in);
    }

    public void exibirMenu() {
        boolean sair = false;
        while (!sair) {
            System.out.println("\n=== MENU ===");
            System.out.println("1 - Planos de Treino");
            System.out.println("2 - Exercícios");
            System.out.println("3 - Seção de Treino");
            System.out.println("4 - Indicadores Biomédicos");
            System.out.println("5 - Relatórios de Evolução");
            System.out.println("6 - Sair");
            System.out.print("Escolha uma opção: ");

            try {
                int opcao = sc.nextInt();
                sc.nextLine();

                switch (opcao) {
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
            } catch (InputMismatchException e) {
                System.out.println("Erro: Entrada inválida. Por favor, digite um número.");
                sc.nextLine();
            }
        }
    }

    public void planoTreino() {
        boolean voltar = false;
        while (!voltar) {
            System.out.println("\n=== PLANOS DE TREINO ===");
            System.out.println("1 - Visualizar planos de treino");
            System.out.println("2 - Cadastrar novo plano de treino");
            System.out.println("3 - Modificar plano de treino");
            System.out.println("4 - Voltar");
            System.out.print("Escolha uma opção: ");

            try {
                int opcao = sc.nextInt();
                sc.nextLine(); // Limpar buffer

                switch (opcao) {
                    case 1:
                        PlanoTreino planoVisualizar = planoTreinoBusiness.carregarPlanoDoUsuario(usuarioLogado);
                        if (planoVisualizar != null){
                            planoTreinoBusiness.exibirPlanoDeTreino(planoVisualizar);
                        }
                        break;
                    case 2:
                        cadastrarPlanoTreino();
                        break;
                    case 3:
                        modificarPlanoTreino();
                        break;
                    case 4:
                        voltar = true;
                        break;
                    default:
                        System.out.println("Opção inválida! Tente novamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Erro: Entrada inválida. Por favor, digite um número.");
                sc.nextLine();
            }
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

    private void coletarExerciciosParaPlano(PlanoTreino plano) {
        boolean continuar = true;
        while (continuar) {
            System.out.print("\nNome da Seção (ex: Treino A - Peito e Tríceps): ");
            String nomeSecao = sc.nextLine();
            SecaoTreino secao = plano.getOuCriarSecao(nomeSecao);

            System.out.print("Nome do exercício a adicionar nesta seção: ");
            String nomeExercicio = sc.nextLine();

            // CORRIGIDO: Busca o exercício no repositório para garantir que ele existe
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

    private void modificarPlanoTreino() {
        System.out.println("\n=== MODIFICAR PLANO DE TREINO ===");

        // 1. Carrega o plano do usuário
        PlanoTreino plano = planoTreinoBusiness.carregarPlanoDoUsuario(usuarioLogado);

        if (plano == null) { // A verificação se o plano existe de fato deve ser feita aqui
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
                        // 3. Salva o plano modificado e encerra o loop
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

    public void exercicios() {
        boolean voltar = false;
        while (!voltar) {
            System.out.println("\n=== GERENCIAR EXERCÍCIOS ===");
            System.out.println("1 - Listar todos os exercícios");
            System.out.println("2 - Cadastrar novo exercício");
            System.out.println("3 - Buscar exercício por nome");
            System.out.println("4 - Atualizar exercício existente");
            System.out.println("5 - Excluir exercício");
            System.out.println("6 - Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            try {
                int opcao = sc.nextInt();
                sc.nextLine();

                switch (opcao) {
                    case 1:
                        listarTodosExercicios();
                        break;
                    case 2:
                        cadastrarNovoExercicio();
                        break;
                    case 3:
                        buscarExercicioPorNome();
                        break;
                    case 4:
                        atualizarExercicioExistente();
                        break;
                    case 5:
                        excluirExercicio();
                        break;
                    case 6:
                        voltar = true;
                        break;
                    default:
                        System.out.println("Opção inválida! Tente novamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Erro: Entrada inválida. Por favor, digite um número.");
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

        } catch (Exception e) {
            System.out.println("Erro ao cadastrar exercício: " + e.getMessage());
        }
    }

    private void buscarExercicioPorNome() {
        System.out.println("\n=== BUSQUE POR EXERCÍCIO ===");

        System.out.print("Digite o nome do exercício: ");
        String nome = sc.nextLine();

        Exercicio exercicio = exercicioBusiness.buscarExercicioPorNome(nome);

        if (exercicio != null) {
            System.out.println("\n--- DETALHES ---");
            System.out.println("Nome: " + exercicio.getNome());
            System.out.println("Descrição: " + exercicio.getDescricao());
            System.out.println("Arquivo GIF: " + (exercicio.getCaminhoGif() != null ? exercicio.getCaminhoGif() : "Não informado"));
        } else {
            System.out.println("Exercício não encontrado.");
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
    }

    public void secaoTreino() {
        System.out.println("\n=== SEÇÃO DE TREINO ===");
        PlanoTreino planoVisualizar = planoTreinoBusiness.carregarPlanoDoUsuario(usuarioLogado);
        List<PlanoTreino> planos = planoTreinoBusiness.exibirPlanoDeTreino(usuarioLogado);

        if (planos.isEmpty()) {
            System.out.println("Você precisa ter um plano de treino cadastrado para iniciar uma seção.");
            return;
        }

        System.out.println("Selecione o plano de treino para a seção:");
        for (int i = 0; i < planos.size(); i++) {
            System.out.println((i + 1) + ". " + planos.get(i).getNomePlano());
        }

        try {
            System.out.print("Escolha o plano (número): ");
            int escolha = sc.nextInt() - 1;
            sc.nextLine();

            if (escolha >= 0 && escolha < planos.size()) {
                PlanoTreino planoSelecionado = planos.get(escolha);
                iniciarSecaoTreino(planoSelecionado);
            } else {
                System.out.println("Opção inválida!");
            }
        } catch (InputMismatchException e) {
            System.out.println("Erro: Digite um número válido.");
            sc.nextLine();
        }
    }

    private void iniciarSecaoTreino(PlanoTreino plano) {
        System.out.println("\n=== INICIANDO SEÇÃO DE TREINO ===");
        System.out.println("Plano: " + plano.getNomePlano());

        secaoTreinoBusiness.iniciarSessao(plano);

        System.out.println("\n1 - Exibir seção como cartão (consulta)");
        System.out.println("2 - Executar treino (preenchimento)");
        System.out.print("Escolha uma opção: ");

        try {
            Scanner sc = new Scanner(System.in);
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
                    
                    // A lógica de registrar a performance real seria aqui,
                    // possivelmente chamando um método em SecaoTreinoBusiness que salva um histórico.
                    // Por enquanto, vamos focar em atualizar o plano.

                    boolean houveDiferenca = (item.getSeries() != seriesRealizadas) || (item.getRepeticoes() != repeticoesRealizadas) || (item.getCarga() != cargaRealizada);

                    if (houveDiferenca) {
                        System.out.print("Performance diferente! Deseja atualizar o plano com os novos parâmetros? (s/n): ");
                        String resposta = sc.nextLine();

                        if (resposta.equalsIgnoreCase("s")) {
                            // O serviço de negócio é chamado para orquestrar a atualização.
                            // A lógica de modificar o item já está dentro do SecaoTreinoBusiness (que chama o PlanoTreinoBusiness).
                            // A UI não precisa modificar o item diretamente.
                            secaoTreinoBusiness.registrarPerformance(usuarioLogado, plano, item, cargaRealizada, repeticoesRealizadas);
                        } else {
                            System.out.println("Plano mantido sem alterações.");
                        }
                    } else {
                        System.out.println("Performance conforme o planejado!");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Erro: Digite valores numéricos válidos.");
                }
            }
        }
        System.out.println("\nSeção de treino concluída!");
    }

    public void indicadoresBio() {
        boolean voltar = false;
        while (!voltar) {
            System.out.println("\n=== INDICADORES BIOMÉDICOS ===");
            System.out.println("1 - Cadastrar indicadores");
            System.out.println("2 - Listar indicadores");
            System.out.println("3 - Modificar indicadores");
            System.out.println("4 - Importar indicadores (CSV)");
            System.out.println("5 - Voltar");
            System.out.print("Escolha uma opção: ");

            try {
                int opcao = sc.nextInt();
                sc.nextLine();

                switch (opcao) {
                    case 1:
                        cadastrarIndicadores();
                        break;
                    case 2:
                        listarIndicadores();
                        break;
                    case 3:
                        modificarIndicadores();
                        break;
                    case 4:
                        importarIndicadoresCSV();
                        break;
                    case 5:
                        voltar = true;
                        break;
                    default:
                        System.out.println("Opção inválida! Tente novamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Erro: Entrada inválida. Por favor, digite um número.");
                sc.nextLine();
            }
        }
    }

    private void cadastrarIndicadores() {
        System.out.println("\n=== CADASTRAR INDICADORES BIOMÉDICOS ===");

        try {
            System.out.print("Peso (kg): ");
            double peso = sc.nextDouble();

            System.out.print("Altura (m): ");
            double altura = sc.nextDouble();

            System.out.print("Percentual de gordura (%): ");
            double percGordura = sc.nextDouble();

            System.out.print("Percentual de massa magra (%): ");
            double percMassaMagra = sc.nextDouble();
            sc.nextLine();

            String id = usuarioLogado.getEmail() + "_" + System.currentTimeMillis();
            IndicadorBiomedico indicador = new IndicadorBiomedico(id, peso, altura, percGordura, percMassaMagra, null);

            indicadorBiomedicoBusiness.cadastrarIndicador(usuarioLogado, indicador);

        } catch (InputMismatchException e) {
            System.out.println("Erro: Digite valores numéricos válidos.");
            sc.nextLine();
        }
    }

    private void listarIndicadores() {
        System.out.println("\n=== SEUS INDICADORES BIOMÉDICOS ===");

        List<IndicadorBiomedico> indicadores = indicadorBiomedicoBusiness.listarIndicadores(usuarioLogado);

        if (indicadores.isEmpty()) {
            System.out.println("Nenhum indicador biomédico cadastrado.");
            return;
        }

        System.out.println("Total de registros: " + indicadores.size());
        System.out.println("-".repeat(60));

        for (int i = 0; i < indicadores.size(); i++) {
            IndicadorBiomedico ind = indicadores.get(i);
            System.out.println("Registro " + (i + 1) + " (ID: " + ind.getId() + ")");
            System.out.println("Peso: " + ind.getPeso() + " kg");
            System.out.println("Altura: " + ind.getAltura() + " m");
            System.out.println("IMC: " + String.format("%.2f", ind.getImc()));
            System.out.println("Gordura: " + ind.getPercentualGordura() + "%");
            System.out.println("Massa Magra: " + ind.getPercentualMassaMagra() + "%");
            System.out.println("-".repeat(60));
        }
    }

    private void modificarIndicadores() {
        System.out.println("\n=== MODIFICAR INDICADORES BIOMÉDICOS ===");

        List<IndicadorBiomedico> indicadores = indicadorBiomedicoBusiness.listarIndicadores(usuarioLogado);

        if (indicadores.isEmpty()) {
            System.out.println("Nenhum indicador para modificar.");
            return;
        }

        System.out.println("Selecione o indicador para modificar:");
        for (int i = 0; i < indicadores.size(); i++) {
            IndicadorBiomedico ind = indicadores.get(i);
            System.out.println((i + 1) + ". ID: " + ind.getId() + " - Peso: " + ind.getPeso() + "kg, IMC: " + String.format("%.2f", ind.getImc()));
        }

        try {
            System.out.print("Escolha o indicador (número): ");
            int escolha = sc.nextInt() - 1;
            sc.nextLine();

            if (escolha >= 0 && escolha < indicadores.size()) {
                IndicadorBiomedico indicadorSelecionado = indicadores.get(escolha);
                modificarIndicador(indicadorSelecionado);
            } else {
                System.out.println("Opção inválida!");
            }
        } catch (InputMismatchException e) {
            System.out.println("Erro: Digite um número válido.");
            sc.nextLine();
        }
    }

    private void modificarIndicador(IndicadorBiomedico indicador) {
        System.out.println("\n=== MODIFICAR INDICADOR ===");

        try {
            System.out.print("Novo peso (atual: " + indicador.getPeso() + "kg): ");
            double novoPeso = sc.nextDouble();

            System.out.print("Nova altura (atual: " + indicador.getAltura() + "m): ");
            double novaAltura = sc.nextDouble();

            System.out.print("Novo percentual de gordura (atual: " + indicador.getPercentualGordura() + "%): ");
            double novoPercGordura = sc.nextDouble();

            System.out.print("Novo percentual de massa magra (atual: " + indicador.getPercentualMassaMagra() + "%): ");
            double novoPercMassaMagra = sc.nextDouble();
            sc.nextLine();

            indicador.setPeso(novoPeso);
            indicador.setAltura(novaAltura);
            indicador.setPercentualGordura(novoPercGordura);
            indicador.setPercentualMassaMagra(novoPercMassaMagra);

            indicadorBiomedicoBusiness.atualizarIndicador(indicador);

        } catch (InputMismatchException e) {
            System.out.println("Erro: Digite valores numéricos válidos.");
            sc.nextLine();
        }
    }

    private void importarIndicadoresCSV() {
        System.out.println("\n=== IMPORTAR INDICADORES BIOMÉDICOS (CSV) ===");
        System.out.println("Formato esperado do CSV:");
        System.out.println("peso,altura,percentualGordura,percentualMassaMagra");
        System.out.println("Exemplo: 70.5,1.75,15.2,84.8");
        System.out.println();

        System.out.print("Digite o caminho completo do arquivo CSV: ");
        String caminhoArquivo = sc.nextLine();

        boolean importado = indicadorBiomedicoBusiness.importarIndicadoresCSV(usuarioLogado, caminhoArquivo);

        if (importado) {
            System.out.println("✅ Indicadores importados com sucesso!");
        } else {
            System.out.println("❌ Erro ao importar indicadores. Verifique o formato do arquivo.");
        }
    }

    public void relatorioEvolucao() {
    }
}
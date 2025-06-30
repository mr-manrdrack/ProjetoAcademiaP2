package br.upe.projetoAcademiaP2.data.repository;

import br.upe.projetoAcademiaP2.data.beans.*;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PlanoTreinoCsvRepository {

    private final String baseDir;
    // O ideal é ter um repositório de exercícios injetado para buscar os exercícios pelo nome
    private final ExercicioRepoImpl exercicioRepository = new ExercicioRepoImpl();

    public PlanoTreinoCsvRepository() {
        // Usando o diretório home do usuário para garantir a portabilidade
        String userHome = System.getProperty("user.home");
        this.baseDir = userHome + File.separator + ".projetoAcademiaP2" + File.separator + "planos" + File.separator;
        File dir = new File(baseDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    private String getArquivoPlano(Usuario usuario) {
        return baseDir + "plano_" + usuario.getEmail().replaceAll("[^a-zA-Z0-9]", "_") + ".csv";
    }

    // ALTERADO: Método salvarPlano agora persiste os metadados do plano na primeira linha
    public void salvarPlano(PlanoTreino plano) {
        Usuario usuario = plano.getUsuario();
        String caminho = getArquivoPlano(usuario);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminho))) {
            // Linha 1: Metadados do Plano (id;nome;inicio_ms;fim_ms)
            // Usamos ';' para os metadados para não conflitar com as vírgulas dos itens
            String metadata = String.format("%d;%s;%d;%d",
                    plano.getId(),
                    plano.getNomePlano(),
                    plano.getInicioPlano().getTime(),
                    plano.getFimPlano().getTime());
            writer.write(metadata);
            writer.newLine();

            // Linha 2: Cabeçalho dos Itens
            writer.write("secao,exercicio,series,repeticoes,carga");
            writer.newLine();

            // Linhas seguintes: Itens do plano
            for (SecaoTreino secao : plano.getSecoes()) {
                for (ItemPlanoTreino item : secao.getItensPlano()) {
                    writer.write(String.format("%s,%s,%d,%d,%d",
                            escape(secao.getNomeTreino()),
                            escape(item.getExercicio().getNome()),
                            item.getSeries(),
                            item.getRepeticoes(),
                            item.getCarga()));
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar plano de treino: " + e.getMessage());
        }
    }

    // ALTERADO: Método carregarPlano agora lê os metadados da primeira linha
    public PlanoTreino carregarPlano(Usuario usuario) {
        String caminho = getArquivoPlano(usuario);
        File file = new File(caminho);
        if (!file.exists()) return null; // Retorna null se não existe plano salvo

        PlanoTreino plano = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(caminho))) {
            // Lê a primeira linha (metadados)
            String metadataLine = reader.readLine();
            if (metadataLine != null) {
                String[] metadata = metadataLine.split(";", -1);
                int id = Integer.parseInt(metadata[0]);
                String nomePlano = metadata[1];
                Date inicioPlano = new Date(Long.parseLong(metadata[2]));
                Date fimPlano = new Date(Long.parseLong(metadata[3]));

                // Cria o objeto PlanoTreino com os dados corretos lidos do arquivo
                plano = new PlanoTreino(id, nomePlano, inicioPlano, fimPlano, usuario);
            } else {
                return null; // Arquivo vazio ou inválido
            }

            // Pula a segunda linha (cabeçalho dos itens)
            reader.readLine();

            // Lê as linhas dos itens
            String linhaItem;
            while ((linhaItem = reader.readLine()) != null) {
                String[] partes = linhaItem.split(",", -1);
                if (partes.length >= 5) {
                    String nomeSecao = unescape(partes[0]);
                    String nomeExercicio = unescape(partes[1]);
                    int series = Integer.parseInt(partes[2]);
                    int repeticoes = Integer.parseInt(partes[3]);
                    int carga = Integer.parseInt(partes[4]);

                    SecaoTreino secao = plano.getOuCriarSecao(nomeSecao);
                    // Busca o exercício completo no repositório de exercícios
                    Exercicio exercicio = exercicioRepository.findByNome(nomeExercicio); 
                    if (exercicio != null) {
                        ItemPlanoTreino item = new ItemPlanoTreino(exercicio, series, repeticoes, carga);
                        secao.addItemSecao(item);
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Erro ao carregar plano de treino: " + e.getMessage());
            // Retorna um plano vazio em caso de erro para não quebrar a aplicação
            return new PlanoTreino(0, "Erro ao carregar", new Date(), new Date(), usuario);
        }

        return plano;
    }

    private String escape(String campo) {
        return campo != null ? campo.replace(",", "\\,") : "";
    }

    private String unescape(String campo) {
        return campo != null ? campo.replace("\\,", ",") : "";
    }
}
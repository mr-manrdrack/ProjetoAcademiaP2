package br.upe.projetoAcademiaP2.data.repository;

import br.upe.projetoAcademiaP2.data.beans.*;

import java.io.*;
import java.util.Date;

public class PlanoTreinoCsvRepository {

    private final String baseDir;
    // O ideal é ter um repositório de exercícios injetado para buscar os exercícios pelo nome
    private final ExercicioRepoImpl exercicioRepository = new ExercicioRepoImpl();

    public PlanoTreinoCsvRepository() {
        // Usando o diretório home do usuário para garantir a portabilidade
        String projectDir = System.getProperty("user.dir");
        
        // 2. Define o caminho para a pasta 'data/planos' dentro do projeto.
        // Assim, os planos ficarão ao lado dos exercícios, mas em sua própria subpasta.
        this.baseDir = projectDir + File.separator + "data" + File.separator + "planos" + File.separator;

        // 3. Garante que a estrutura de pastas (data/planos/) exista.
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

    
    public PlanoTreino carregarPlano(Usuario usuario) {
        String caminho = getArquivoPlano(usuario);
        File file = new File(caminho);
        if (!file.exists()) return null;

        PlanoTreino plano = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(caminho))) {
            String metadataLine = reader.readLine();
            if (metadataLine != null) {
                String[] metadata = metadataLine.split(";", -1);
                int id = Integer.parseInt(metadata[0]);
                String nomePlano = metadata[1];
                Date inicioPlano = new Date(Long.parseLong(metadata[2]));
                Date fimPlano = new Date(Long.parseLong(metadata[3]));

                plano = new PlanoTreino(id, nomePlano, inicioPlano, fimPlano, usuario);
            } else {
                return null;
            }

            reader.readLine(); 

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
                    Exercicio exercicio = exercicioRepository.findByNome(nomeExercicio);
                    
                    // CORREÇÃO: Adicionada uma mensagem de erro para o usuário
                    if (exercicio != null) {
                        ItemPlanoTreino item = new ItemPlanoTreino(exercicio, series, repeticoes, carga);
                        secao.addItemSecao(item);
                    } else {
                        // Esta mensagem agora te dirá qual exercício está faltando.
                        System.err.println("AVISO: O exercício '" + nomeExercicio + "' listado no plano de treino não foi encontrado no arquivo de exercícios e será ignorado.");
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Erro ao carregar plano de treino: " + e.getMessage());
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
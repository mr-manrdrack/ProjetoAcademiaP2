package br.upe.projetoAcademiaP2.data.repository;

import br.upe.projetoAcademiaP2.data.beans.*;

import java.io.*;
import java.text.ParseException; // Adicionado para lidar com ParseException
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList; // Certifique-se de que ArrayList está importado

public class PlanoTreinoCsvRepository {

    private final String baseDir;
    private final ExercicioRepoImpl exercicioRepository = new ExercicioRepoImpl();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public PlanoTreinoCsvRepository() {
        String projectDir = System.getProperty("user.dir");
        this.baseDir = projectDir + File.separator + "data" + File.separator + "planos" + File.separator;

        File dir = new File(baseDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    private String getArquivoPlano(Usuario usuario) {
        return baseDir + "plano_" + usuario.getEmail().replaceAll("[^a-zA-Z0-9]", "_") + ".csv";
    }

    public void salvarPlano(PlanoTreino plano) {
        String filePath = getArquivoPlano(plano.getUsuario());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("id,nomePlano,inicioPlano,fimPlano,emailUsuario\n");


            writer.write(String.format("%d,%s,%s,%s,%s\n",
                    plano.getId(),
                    escape(plano.getNomePlano()),
                    dateFormat.format(plano.getInicioPlano()),
                    dateFormat.format(plano.getFimPlano()),
                    escape(plano.getUsuario().getEmail())));


            for (SecaoTreino secao : plano.getSecoes()) {
                for (ItemPlanoTreino item : secao.getItensPlano()) {
                    writer.write(String.format("%s,%s,%d,%d,%d\n",
                            escape(secao.getNomeTreino()),
                            escape(item.getExercicio().getNome()),
                            item.getSeries(),
                            item.getRepeticoes(),
                            item.getCarga()));
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar plano de treino: " + e.getMessage());
        }
    }

    public PlanoTreino carregarPlano(Usuario usuario) {
        String arquivoPlano = getArquivoPlano(usuario);
        File file = new File(arquivoPlano);


        if (!file.exists()) {
            return new PlanoTreino(0, "Erro ao carregar", new Date(), new Date(), usuario);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivoPlano))) {

            reader.readLine();


            String metadataLine = reader.readLine();
            if (metadataLine == null || metadataLine.trim().isEmpty()) {
                System.err.println("Erro ao carregar plano de treino: Metadados do plano ausentes ou arquivo corrompido.");
                return new PlanoTreino(0, "Erro ao carregar", new Date(), new Date(), usuario);
            }

            String[] metadata = metadataLine.split(",", -1);
            if (metadata.length < 5) {
                System.err.println("Erro ao carregar plano de treino: Metadados incompletos.");
                return new PlanoTreino(0, "Erro ao carregar", new Date(), new Date(), usuario);
            }

            int id = Integer.parseInt(metadata[0]);
            String nomePlano = unescape(metadata[1]);
            Date inicioPlano = dateFormat.parse(metadata[2]);
            Date fimPlano = dateFormat.parse(metadata[3]);


            PlanoTreino plano = new PlanoTreino(id, nomePlano, inicioPlano, fimPlano, usuario);

            String line;
            while ((line = reader.readLine()) != null) {
                String[] partes = line.split(",", -1);
                if (partes.length >= 5) {
                    String nomeSecao = unescape(partes[0]);
                    String nomeExercicio = unescape(partes[1]);
                    int series = Integer.parseInt(partes[2]);
                    int repeticoes = Integer.parseInt(partes[3]);
                    int carga = Integer.parseInt(partes[4]);

                    SecaoTreino secao = plano.getOuCriarSecao(nomeSecao);
                    Exercicio exercicio = exercicioRepository.findByNome(nomeExercicio);

                    if (exercicio != null) {
                        ItemPlanoTreino item = new ItemPlanoTreino(exercicio, series, repeticoes, carga);
                        secao.addItemSecao(item);
                    } else {
                        System.err.println("AVISO: O exercício '" + nomeExercicio + "' listado no plano de treino não foi encontrado no arquivo de exercícios e será ignorado.");
                    }
                }
            }
            return plano;

        } catch (IOException | ParseException | NumberFormatException e) {
            System.err.println("Erro ao carregar plano de treino: " + e.getMessage());

            return new PlanoTreino(0, "Erro ao carregar", new Date(), new Date(), usuario);
        }
    }

    private String escape(String campo) {
        return campo != null ? campo.replace(",", "\\,") : "";
    }

    private String unescape(String campo) {
        return campo != null ? campo.replace("\\,", ",") : "";
    }
}
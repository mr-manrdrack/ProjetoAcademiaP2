package br.upe.projetoAcademiaP2.data.repository;

import br.upe.projetoAcademiaP2.data.beans.Exercicio;
import br.upe.projetoAcademiaP2.data.repository.interfaces.IExercicioRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ExercicioRepoImpl implements IExercicioRepository {

    private List<Exercicio> exercicios = new ArrayList<>();
    private final String filePath;

    public ExercicioRepoImpl() {
        this.filePath = System.getProperty("user.dir") + "/data/exercicios.csv";
        criarDiretorioSeNecessario();
        carregarDoCsv();
    }

    private void criarDiretorioSeNecessario() {
        File file = new File(filePath);
        File parent = file.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }
    }

    @Override
    public Exercicio create(Exercicio exercicio) {
        if (findByNome(exercicio.getNome()) != null) {
            System.err.println("Erro: Já existe um exercício com o nome '" + exercicio.getNome() + "'.");
            return null;
        }
        exercicios.add(exercicio);
        persistirNoCsv();
        return exercicio;
    }

    @Override
    public List<Exercicio> findAll() {
        return new ArrayList<>(exercicios);
    }

    @Override
    public Exercicio findByNome(String nome) {
        for (Exercicio e : exercicios) {
            if (e.getNome().equalsIgnoreCase(nome)) {
                return e;
            }
        }
        return null;
    }

    @Override
    public Exercicio update(Exercicio e) {
        Exercicio existente = findByNome(e.getNome());
        if (existente != null) {
            existente.setDescricao(e.getDescricao());
            existente.setCaminhoGif(e.getCaminhoGif());
            persistirNoCsv();
            return existente;
        }
        return null;
    }

    @Override
    public boolean delete(String nome) {
        Exercicio exercicio = findByNome(nome);
        if (exercicio != null) {
            boolean removido = exercicios.remove(exercicio);
            if (removido) persistirNoCsv();
            return removido;
        }
        return false;
    }

    private void persistirNoCsv() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("nome,descricao,caminhoGif");
            writer.newLine();
            for (Exercicio e : exercicios) {
                writer.write(String.format("%s,%s,%s",
                        escape(e.getNome()),
                        escape(e.getDescricao()),
                        escape(e.getCaminhoGif())));
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar CSV de exercícios: " + e.getMessage());
        }
    }

    private void carregarDoCsv() {
        File file = new File(filePath);
        if (!file.exists()) {
            persistirNoCsv();
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            reader.readLine(); // pular cabeçalho
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(",", -1);
                if (partes.length >= 3) {
                    Exercicio e = new Exercicio(
                            unescape(partes[0]),
                            unescape(partes[1]),
                            unescape(partes[2])
                    );
                    exercicios.add(e);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar CSV de exercícios: " + e.getMessage());
        }
    }

    private String escape(String campo) {
        return campo != null ? campo.replace(",", "\\,") : "";
    }

    private String unescape(String campo) {
        return campo != null ? campo.replace("\\,", ",") : "";
    }
}

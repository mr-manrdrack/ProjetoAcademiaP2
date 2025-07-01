package br.upe.projetoAcademiaP2.data.repository;

import br.upe.projetoAcademiaP2.data.beans.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlanoTreinoCsvRepositoryTest {

    private PlanoTreinoCsvRepository planoRepository;
    private String originalUserDir;

    @TempDir
    Path tempDir;

    private Path dataDir;
    private Path planosDir;
    private Path exerciciosFile;

    @BeforeEach
    void setUp() throws IOException {
        originalUserDir = System.getProperty("user.dir");

        System.setProperty("user.dir", tempDir.toAbsolutePath().toString());

        dataDir = tempDir.resolve("data");
        planosDir = dataDir.resolve("planos");
        exerciciosFile = dataDir.resolve("exercicios.csv");

        Files.createDirectories(planosDir);

        String exerciciosContent = "nome,descricao,caminhoGif\n" +
                "Supino,Para peito,supino.gif\n" +
                "Agachamento,Para pernas,agachamento.gif\n" +
                "Puxada,Para costas,puxada.gif";
        Files.write(exerciciosFile, exerciciosContent.getBytes());

        planoRepository = new PlanoTreinoCsvRepository();
    }

    @AfterEach
    void tearDown() {

        System.setProperty("user.dir", originalUserDir);
    }

    @Test
    void testSalvarPlano() throws IOException {
        Usuario usuario = new Comum("user@example.com", "User Test", "senha123", "11987654321", 70.0, 1.75, 20.0);
        Date inicio = new Date();
        Date fim = new Date(inicio.getTime() + 7 * 24 * 60 * 60 * 1000);

        PlanoTreino plano = new PlanoTreino(1, "Plano de Teste", inicio, fim, usuario);

        SecaoTreino secao = plano.getOuCriarSecao("Treino A");
        Exercicio supino = new Exercicio("Supino", "Para peito", "supino.gif");
        secao.addItemSecao(new ItemPlanoTreino(supino, 3, 10, 50));

        planoRepository.salvarPlano(plano);

        Path savedFilePath = planosDir.resolve("plano_" + usuario.getEmail().replaceAll("[^a-zA-Z0-9]", "_") + ".csv");
        assertTrue(Files.exists(savedFilePath), "O arquivo do plano deve ser salvo.");

        List<String> lines = Files.readAllLines(savedFilePath);
        assertFalse(lines.isEmpty(), "O arquivo salvo não deve estar vazio.");

        // Verifica se a primeira linha é o cabeçalho
        assertTrue(lines.get(0).startsWith("id,nomePlano,inicioPlano,fimPlano,emailUsuario"), "O cabeçalho do CSV de metadados está incorreto.");
        // Verifica se a segunda linha contém os metadados do plano
        assertTrue(lines.get(1).contains("1,Plano de Teste,"), "Os metadados do plano estão incorretos.");
        // Verifica se as linhas subsequentes contêm os itens do plano
        assertTrue(lines.contains("Treino A,Supino,3,10,50"), "Os itens do plano de treino estão incorretos.");
    }

    @Test
    void testCarregarPlano() throws IOException {
        Usuario usuario = new Comum("loaduser@example.com", "Load User", "loadpass", "11999999999", 75.0, 1.80, 15.0);
        Date inicio = new Date();
        Date fim = new Date(inicio.getTime() + 14 * 24 * 60 * 60 * 1000);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Path planoFilePath = planosDir.resolve("plano_" + usuario.getEmail().replaceAll("[^a-zA-Z0-9]", "_") + ".csv");


        String planoContent = String.format("id,nomePlano,inicioPlano,fimPlano,emailUsuario\n" + // Cabeçalho
                        "2,Plano Carregado,%s,%s,%s\n" +
                        "Treino B,Agachamento,4,12,60\n" +
                        "Treino B,Supino,3,8,45\n",
                dateFormat.format(inicio), dateFormat.format(fim), usuario.getEmail());
        Files.write(planoFilePath, planoContent.getBytes());

        PlanoTreino loadedPlano = planoRepository.carregarPlano(usuario);

        assertNotNull(loadedPlano, "O plano carregado não deve ser nulo.");
        assertEquals(2, loadedPlano.getId(), "O ID do plano deve ser 2.");
        assertEquals("Plano Carregado", loadedPlano.getNomePlano(), "O nome do plano deve ser 'Plano Carregado'.");
        assertEquals(usuario.getEmail(), loadedPlano.getUsuario().getEmail(), "O email do usuário deve corresponder.");

        List<SecaoTreino> secoes = loadedPlano.getSecoes();
        assertFalse(secoes.isEmpty(), "Deve haver seções no plano carregado.");
        assertEquals(1, secoes.size(), "Deve haver apenas uma seção (Treino B).");
        assertEquals("Treino B", secoes.get(0).getNomeTreino(), "O nome da seção deve ser 'Treino B'.");

        List<ItemPlanoTreino> itens = secoes.get(0).getItensPlano();
        assertNotNull(itens, "A lista de itens da seção não deve ser nula.");
        assertEquals(2, itens.size(), "A seção deve conter 2 itens.");

        ItemPlanoTreino item1 = itens.get(0);
        assertEquals("Agachamento", item1.getExercicio().getNome(), "O primeiro item deve ser 'Agachamento'.");
        assertEquals(4, item1.getSeries(), "O número de séries do primeiro item está incorreto.");
        assertEquals(12, item1.getRepeticoes(), "O número de repetições do primeiro item está incorreto.");
        assertEquals(60, item1.getCarga(), "A carga do primeiro item está incorreta.");

        ItemPlanoTreino item2 = itens.get(1);
        assertEquals("Supino", item2.getExercicio().getNome(), "O segundo item deve ser 'Supino'.");
        assertEquals(3, item2.getSeries(), "O número de séries do segundo item está incorreto.");
        assertEquals(8, item2.getRepeticoes(), "O número de repetições do segundo item está incorreto.");
        assertEquals(45, item2.getCarga(), "A carga do segundo item está incorreta.");
    }

    @Test
    void testCarregarPlanoInexistente() {
        Usuario usuario = new Comum("nonexistent@example.com", "Non Existent", "pass", "123", 60.0, 1.60, 25.0);

        PlanoTreino loadedPlano = planoRepository.carregarPlano(usuario);

        assertNotNull(loadedPlano, "O plano carregado não deve ser nulo (espera-se um plano de erro).");
        assertEquals(0, loadedPlano.getId(), "O ID deve ser 0 para um plano 'erro'.");
        assertEquals("Erro ao carregar", loadedPlano.getNomePlano(), "O nome do plano deve indicar erro.");
    }
}
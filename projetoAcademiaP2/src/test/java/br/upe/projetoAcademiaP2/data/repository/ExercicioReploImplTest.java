package br.upe.projetoAcademiaP2.data.repository;

import br.upe.projetoAcademiaP2.data.beans.Exercicio;
import org.junit.jupiter.api.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExercicioRepoImplTest {

    private static final String TEST_BASE_DIR = System.getProperty("user.dir") + File.separator + "test-data";
    private static final String TEST_CSV_PATH = TEST_BASE_DIR + File.separator + "data" + File.separator + "exercicios.csv";
    private ExercicioRepoImpl repository;

    @BeforeAll
    static void setupAll() {
        System.setProperty("user.dir.for.exercicio.tests", System.getProperty("user.dir")); // Salva o dir original
        System.setProperty("user.dir", TEST_BASE_DIR);
        new File(TEST_BASE_DIR + File.separator + "data").mkdirs(); // Garante que a pasta 'data' exista dentro de test-data
    }

    @BeforeEach
    void setUp() {
        File testCsvFile = new File(TEST_CSV_PATH);
        if (testCsvFile.exists()) {
            testCsvFile.delete();
        }
        repository = new ExercicioRepoImpl();
        assertEquals(0, repository.findAll().size(), "O repositório deve estar vazio no início de cada teste.");
    }

    @AfterEach
    void tearDown() {

    }

    @AfterAll
    static void tearDownAll() throws IOException {

        Files.walk(Paths.get(TEST_BASE_DIR))
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);

        System.setProperty("user.dir", System.getProperty("user.dir.for.exercicio.tests"));
        System.clearProperty("user.dir.for.exercicio.tests");
    }

    @Test
    @DisplayName("Deve criar um novo exercício e persistir no CSV")
    void testCreateExercicio() {
        Exercicio exercicio = new Exercicio("Flexão", "Flexão de braço", "caminho/flexao.gif");
        Exercicio createdExercicio = repository.create(exercicio);

        assertNotNull(createdExercicio, "O exercício criado não deve ser nulo");
        assertEquals("Flexão", createdExercicio.getNome(), "O nome do exercício criado deve ser 'Flexão'");

        List<Exercicio> exercicios = repository.findAll();
        assertEquals(1, exercicios.size(), "Deve haver exatamente 1 exercício após a criação");
        assertEquals("Flexão", exercicios.get(0).getNome(), "O nome do exercício na lista deve ser 'Flexão'");
    }

    @Test
    @DisplayName("Deve encontrar exercício por nome")
    void testFindByNome() {
        repository.create(new Exercicio("Agachamento", "Agachamento libre", "agach.gif"));

        Exercicio found = repository.findByNome("Agachamento");
        assertNotNull(found, "Exercício 'Agachamento' deve ser encontrado");
        assertEquals("Agachamento", found.getNome(), "O nome do exercício encontrado deve ser 'Agachamento'");
        assertEquals("Agachamento libre", found.getDescricao(), "A descrição do exercício encontrado deve ser 'Agachamento libre'");

        assertNull(repository.findByNome("Caminhada"), "Exercício 'Caminhada' não deve ser encontrado");
    }

    @Test
    @DisplayName("Deve atualizar um exercício existente")
    void testUpdateExercicio() {
        Exercicio original = new Exercicio("Supino", "Supino Reto", "supino.gif");
        repository.create(original);

        original.setDescricao("Supino Inclinado");
        original.setCaminhoGif("supino_incl.gif");

        Exercicio updated = repository.update(original);
        assertNotNull(updated, "O exercício atualizado não deve ser nulo");
        assertEquals("Supino Inclinado", updated.getDescricao(), "A descrição deve ser atualizada para 'Supino Inclinado'");
        assertEquals("supino_incl.gif", updated.getCaminhoGif(), "O caminho do GIF deve ser atualizado");

        Exercicio loaded = repository.findByNome("Supino");
        assertNotNull(loaded, "O exercício 'Supino' deve ser carregado após a atualização");
        assertEquals("Supino Inclinado", loaded.getDescricao(), "A descrição do exercício carregado deve ser a nova descrição");
    }

    @Test
    @DisplayName("Não deve atualizar exercício inexistente")
    void testUpdateNonExistingExercicio() {
        Exercicio nonExisting = new Exercicio("NaoExiste", "Desc", "path");
        assertNull(repository.update(nonExisting), "Não deve ser possível atualizar um exercício que não existe");
    }

    @Test
    @DisplayName("Deve deletar um exercício existente")
    void testDeleteExercicio() {
        repository.create(new Exercicio("Remada", "Remada baixa", "remada.gif"));
        repository.create(new Exercicio("Corrida", "Corrida na esteira", "corrida.gif"));
        assertEquals(2, repository.findAll().size(), "Deve haver 2 exercícios antes da deleção");


        assertTrue(repository.delete("Remada"), "A deleção de 'Remada' deve retornar true");
        assertNull(repository.findByNome("Remada"), "Exercício 'Remada' não deve mais ser encontrado");
        assertEquals(1, repository.findAll().size(), "Deve haver 1 exercício após a deleção de 'Remada'");
        assertEquals("Corrida", repository.findAll().get(0).getNome(), "O exercício restante deve ser 'Corrida'");

        assertFalse(repository.delete("NaoExiste"), "A deleção de 'NaoExiste' deve retornar false");
        assertEquals(1, repository.findAll().size(), "A quantidade de exercícios deve permanecer 1");
    }

    @Test
    @DisplayName("Deve listar todos os exercícios")
    void testFindAll() {
        assertEquals(0, repository.findAll().size(), "A lista deve estar vazia no início do teste");
        repository.create(new Exercicio("Ex1", "D1", "C1"));
        repository.create(new Exercicio("Ex2", "D2", "C2"));

        List<Exercicio> all = repository.findAll();
        assertEquals(2, all.size(), "A lista deve conter 2 exercícios");
        assertTrue(all.stream().anyMatch(e -> e.getNome().equals("Ex1")), "Deve conter 'Ex1'");
        assertTrue(all.stream().anyMatch(e -> e.getNome().equals("Ex2")), "Deve conter 'Ex2'");
    }

    @Test
    @DisplayName("Não deve criar exercício com nome duplicado")
    void testCreateDuplicateExercicio() {
        Exercicio exercicio1 = new Exercicio("Puxada", "Puxada alta", "puxada.gif");
        repository.create(exercicio1);

        Exercicio exercicio2 = new Exercicio("Puxada", "Outra descrição", "outropuxada.gif");
        Exercicio createdExercicio2 = repository.create(exercicio2);

        assertNull(createdExercicio2, "Não deve ser possível criar um exercício com nome duplicado");
        assertEquals(1, repository.findAll().size(), "A quantidade de exercícios deve permanecer 1 após tentativa de duplicata");
        assertEquals("Puxada alta", repository.findByNome("Puxada").getDescricao(), "A descrição do exercício original deve permanecer");
    }
}

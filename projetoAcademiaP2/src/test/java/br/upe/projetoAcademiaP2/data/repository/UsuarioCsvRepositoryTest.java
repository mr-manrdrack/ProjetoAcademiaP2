package br.upe.projetoAcademiaP2.data.repository;

import br.upe.projetoAcademiaP2.data.beans.Adm;
import br.upe.projetoAcademiaP2.data.beans.Comum;
import br.upe.projetoAcademiaP2.data.beans.Usuario;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioCsvRepositoryTest {

    private UsuarioCsvRepository usuarioRepository;
    private Path tempDir;
    private Path tempCsvPath;

    @BeforeEach
    void setUp() throws IOException {
        tempDir = Files.createTempDirectory("teste-usuarios");
        tempCsvPath = tempDir.resolve("usuarios.csv");
        usuarioRepository = new UsuarioCsvRepository(tempCsvPath.toString());
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.walk(tempDir)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
    }

    @Test
    @DisplayName("Deve criar um novo usuário com sucesso")
    void testCreate_NovoUsuario() {
        Usuario comum = new Comum("Usuario Comum", "123456789", "comum@test.com", "senha123", 70.5, 1.75, 15.2);
        Usuario usuarioCriado = usuarioRepository.create(comum);

        assertNotNull(usuarioCriado);
        assertEquals("comum@test.com", usuarioCriado.getEmail());

        Usuario usuarioEncontrado = usuarioRepository.findByEmail("comum@test.com");
        assertNotNull(usuarioEncontrado);
        assertEquals("Usuario Comum", usuarioEncontrado.getNome());
    }

    @Test
    @DisplayName("Não deve criar um usuário com email já existente")
    void testCreate_UsuarioExistente() {
        Usuario adm = new Adm("Admin", "987654321", "admin@test.com", "admin123", null, null, null);
        usuarioRepository.create(adm);

        Usuario admDuplicado = new Adm("Admin 2", "111222333", "admin@test.com", "outrasenha", null, null, null);
        Usuario resultado = usuarioRepository.create(admDuplicado);

        assertNull(resultado);
        assertEquals(1, usuarioRepository.listarTodos().size());
    }

    @Test
    @DisplayName("Deve encontrar um usuário pelo email")
    void testFindByEmail_UsuarioExistente() {
        Usuario comum = new Comum("Usuario Teste", "555555555", "teste@find.com", "findpass", 80.0, 1.80, 20.0);
        usuarioRepository.create(comum);

        Usuario usuarioEncontrado = usuarioRepository.findByEmail("teste@find.com");

        assertNotNull(usuarioEncontrado);
        assertEquals("teste@find.com", usuarioEncontrado.getEmail());
    }

    @Test
    @DisplayName("Deve retornar nulo ao procurar um email inexistente")
    void testFindByEmail_UsuarioInexistente() {
        Usuario usuarioEncontrado = usuarioRepository.findByEmail("naoexiste@test.com");
        assertNull(usuarioEncontrado);
    }

    @Test
    @DisplayName("Deve atualizar os dados de um usuário existente")
    void testUpdate_UsuarioExistente() {
        Usuario comum = new Comum("Original", "111111111", "update@test.com", "originalpass", 60.0, 1.60, 25.0);
        usuarioRepository.create(comum);

        Usuario usuarioAtualizado = new Comum("Atualizado", "222222222", "update@test.com", "novapass", 62.0, 1.61, 22.5);
        usuarioRepository.update(usuarioAtualizado);

        Usuario usuarioEncontrado = usuarioRepository.findByEmail("update@test.com");

        assertNotNull(usuarioEncontrado);
        assertEquals("Atualizado", usuarioEncontrado.getNome());
        assertEquals("222222222", usuarioEncontrado.getTelefone());
        assertEquals(62.0, usuarioEncontrado.getPesoAtual());
    }

    @Test
    @DisplayName("Deve deletar um usuário existente com sucesso")
    void testDelete_UsuarioExistente() {
        Usuario adm = new Adm("Para Deletar", "333333333", "delete@test.com", "delpass", null, null, null);
        usuarioRepository.create(adm);

        boolean deletado = usuarioRepository.delete("delete@test.com");

        assertTrue(deletado);
        assertNull(usuarioRepository.findByEmail("delete@test.com"));
    }

    @Test
    @DisplayName("Não deve deletar um usuário inexistente")
    void testDelete_UsuarioInexistente() {
        boolean deletado = usuarioRepository.delete("naoexiste@delete.com");
        assertFalse(deletado);
    }

    @Test
    @DisplayName("Deve listar todos os usuários corretamente")
    void testListarTodos() {
        assertEquals(0, usuarioRepository.listarTodos().size());

        Usuario adm = new Adm("Admin List", "444444444", "list1@test.com", "listpass1", null, null, null);
        Usuario comum = new Comum("Comum List", "555555555", "list2@test.com", "listpass2", 75.0, 1.85, 18.0);

        usuarioRepository.create(adm);
        usuarioRepository.create(comum);

        List<Usuario> todos = usuarioRepository.listarTodos();

        assertEquals(2, todos.size());
        assertTrue(todos.stream().anyMatch(u -> u.getEmail().equals("list1@test.com")));
        assertTrue(todos.stream().anyMatch(u -> u.getEmail().equals("list2@test.com")));
    }

    @Test
    @DisplayName("Deve carregar os dados corretamente do CSV ao iniciar")
    void testCarregarDoCsv() {
        Usuario adm = new Adm("Admin Load", "777", "load_adm@test.com", "load1", null, null, null);
        Usuario comum = new Comum("Comum Load", "888", "load_comum@test.com", "load2", 90.0, 1.90, 22.0);

        usuarioRepository.create(adm);
        usuarioRepository.create(comum);

        UsuarioCsvRepository novoRepositorio = new UsuarioCsvRepository(tempCsvPath.toString());
        List<Usuario> usuariosCarregados = novoRepositorio.listarTodos();

        assertEquals(2, usuariosCarregados.size());

        Usuario admCarregado = novoRepositorio.findByEmail("load_adm@test.com");
        assertNotNull(admCarregado);
        assertTrue(admCarregado instanceof Adm);
        assertEquals("Admin Load", admCarregado.getNome());

        Usuario comumCarregado = novoRepositorio.findByEmail("load_comum@test.com");
        assertNotNull(comumCarregado);
        assertTrue(comumCarregado instanceof Comum);
        assertEquals(90.0, comumCarregado.getPesoAtual());
    }
}
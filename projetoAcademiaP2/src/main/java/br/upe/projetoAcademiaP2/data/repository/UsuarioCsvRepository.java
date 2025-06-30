package br.upe.projetoAcademiaP2.data.repository;

import br.upe.projetoAcademiaP2.data.beans.Adm;
import br.upe.projetoAcademiaP2.data.beans.Comum;
import br.upe.projetoAcademiaP2.data.beans.Usuario;
import br.upe.projetoAcademiaP2.data.repository.interfaces.IUsuarioRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioCsvRepository implements IUsuarioRepository {

    private final String filePath;
    private static final String CSV_HEADER = "tipo,email,nome,senha,telefone,peso,altura,gordura";
    private List<Usuario> usuarios;

    public UsuarioCsvRepository() {
        this.filePath = obterCaminhoCsv();
        criarDiretorioSeNecessario(); // Garante que a pasta "data/" exista
        this.usuarios = new ArrayList<>();
        carregarDoCsv();
    }

    /**
     * Define o caminho para salvar o CSV fora da pasta src,
     * dentro de uma pasta chamada "data/" na raiz do projeto.
     */
    private String obterCaminhoCsv() {
        String basePath = System.getProperty("user.dir"); // raiz do projeto
        String relativePath = "/data/usuarios.csv";
        return basePath + relativePath;
    }

    /**
     * Cria a pasta "data/" caso ela ainda não exista.
     */
    private void criarDiretorioSeNecessario() {
        File file = new File(this.filePath);
        File parent = file.getParentFile();
        if (!parent.exists()) {
            boolean criada = parent.mkdirs();
            if (criada) {
                System.out.println(" Pasta 'data/' criada: " + parent.getAbsolutePath());
            } else {
                System.err.println(" Falha ao criar pasta 'data/': " + parent.getAbsolutePath());
            }
        }
    }

    @Override
    public Usuario create(Usuario usuario) {
        if (findByEmail(usuario.getEmail()) == null) {
            this.usuarios.add(usuario);
            persistirNoCsv();
            System.out.println(" Usuário criado: " + usuario.getEmail());
            return usuario;
        }
        System.out.println(" Usuário já existe: " + usuario.getEmail());
        return null;
    }

    @Override
    public Usuario findByEmail(String email) {
        return this.usuarios.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Usuario update(Usuario usuario) {
        this.usuarios.removeIf(u -> u.getEmail().equalsIgnoreCase(usuario.getEmail()));
        this.usuarios.add(usuario);
        persistirNoCsv();
        System.out.println(" Usuário atualizado: " + usuario.getEmail());
        return usuario;
    }

    @Override
    public boolean delete(String email) {
        boolean removido = this.usuarios.removeIf(u -> u.getEmail().equalsIgnoreCase(email));
        if (removido) {
            persistirNoCsv();
            System.out.println(" Usuário removido: " + email);
        } else {
            System.out.println(" Usuário não encontrado para remoção: " + email);
        }
        return removido;
    }

    @Override
    public List<Usuario> listarTodos() {
        System.out.println(" Listando usuários... Total: " + this.usuarios.size());
        for (Usuario u : this.usuarios) {
            System.out.println("   - " + u.getEmail() + " (" + u.getNome() + ")");
        }
        return new ArrayList<>(this.usuarios);
    }

    private void persistirNoCsv() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.filePath))) {
            writer.write(CSV_HEADER);
            writer.newLine();
            for (Usuario u : this.usuarios) {
                String tipo = (u instanceof Adm) ? "ADM" : "COMUM";
                String linha = String.join(",",
                        tipo,
                        u.getEmail(),
                        u.getNome(),
                        u.getSenha(),
                        u.getTelefone() != null ? u.getTelefone() : "",
                        u.getPesoAtual() != null ? u.getPesoAtual().toString() : "",
                        u.getAlturaAtual() != null ? u.getAlturaAtual().toString() : "",
                        u.getPercGorduraAtual() != null ? u.getPercGorduraAtual().toString() : ""
                );
                writer.write(linha);
                writer.newLine();
            }
            System.out.println("💾 CSV salvo com sucesso: " + this.filePath);
        } catch (IOException e) {
            System.err.println("❌ Erro ao salvar CSV: " + e.getMessage());
        }
    }

    private void carregarDoCsv() {
        File file = new File(this.filePath);
        if (!file.exists()) {
            System.out.println(" Arquivo CSV não encontrado. Criando novo.");
            persistirNoCsv();
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(this.filePath))) {
            reader.readLine(); // Pula o cabeçalho
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(",", -1); // -1 para pegar campos vazios

                if (dados.length < 4) continue;

                Usuario usuario = "ADM".equals(dados[0]) ? new Adm() : new Comum();

                usuario.setEmail(dados[1]);
                usuario.setNome(dados[2]);
                usuario.setSenha(dados[3]);

                if (dados.length > 4 && !dados[4].isEmpty()) usuario.setTelefone(dados[4]);
                if (dados.length > 5 && !dados[5].isEmpty()) usuario.setPesoAtual(Double.parseDouble(dados[5]));
                if (dados.length > 6 && !dados[6].isEmpty()) usuario.setAlturaAtual(Double.parseDouble(dados[6]));
                if (dados.length > 7 && !dados[7].isEmpty()) usuario.setPercGorduraAtual(Double.parseDouble(dados[7]));

                this.usuarios.add(usuario);
                
            }
        } catch (IOException e) {
            System.err.println(" Erro ao carregar CSV: " + e.getMessage());
        }
    }
}

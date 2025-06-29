package br.upe.projetoAcademiaP2.data.repository;

import br.upe.projetoAcademiaP2.data.beans.Adm;
import br.upe.projetoAcademiaP2.data.beans.Comum;
import br.upe.projetoAcademiaP2.data.beans.Usuario;
import br.upe.projetoAcademiaP2.data.repository.interfaces.IUsuarioRepository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioCsvRepository implements IUsuarioRepository {

    private final String filePath;
    private static final String CSV_HEADER = "tipo,email,nome,senha,telefone,peso,altura,gordura";
    private List<Usuario> usuarios;

    public UsuarioCsvRepository() {

        String userHome = System.getProperty("user.home");
        
    
        String appDirName = ".projetoAcademiaP2";
        
        
        File appDir = new File(userHome, appDirName);
        
        
        if (!appDir.exists()) {
            boolean created = appDir.mkdirs(); 
            if (!created) {
                System.err.println("Falha ao criar o diretório da aplicação: " + appDir.getPath());
            }
        }
        
        
        this.filePath = appDir.getPath() + File.separator + "usuarios.csv";
        
        

        this.usuarios = new ArrayList<>();
        carregarDoCsv(); 
    }

    
    @Override
    public Usuario create(Usuario usuario) {
        if (findByEmail(usuario.getEmail()) == null) {
            this.usuarios.add(usuario);
            persistirNoCsv();
            return usuario;
        }
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
        return usuario;
    }

    @Override
    public boolean delete(String email) {
        boolean removido = this.usuarios.removeIf(u -> u.getEmail().equalsIgnoreCase(email));
        if (removido) {
            persistirNoCsv();
        }
        return removido;
    }

    @Override
    public List<Usuario> listarTodos() {
        return new ArrayList<>(this.usuarios);
    }


    private void persistirNoCsv() {
        // Este método agora usa a variável de instância 'filePath'
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
        } catch (IOException e) {
            System.err.println("Erro ao persistir dados no CSV: " + e.getMessage());
        }
    }

    private void carregarDoCsv() {
        // Este método agora usa a variável de instância 'filePath'
        File file = new File(this.filePath);
        if (!file.exists()) {
            persistirNoCsv();
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(this.filePath))) {
            reader.readLine(); 
            
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(",");
                Usuario usuario = null;
                if ("ADM".equals(dados[0])) {
                    usuario = new Adm();
                } else {
                    usuario = new Comum();
                }
                
                usuario.setEmail(dados[1]);
                usuario.setNome(dados[2]);
                usuario.setSenha(dados[3]);
                
                this.usuarios.add(usuario);
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar dados do CSV: " + e.getMessage());
        }
    }
}
package br.upe.projetoAcademiaP2.business;

import br.upe.projetoAcademiaP2.data.beans.Adm;
import br.upe.projetoAcademiaP2.data.beans.Comum;
import br.upe.projetoAcademiaP2.data.beans.Usuario;
import br.upe.projetoAcademiaP2.data.repository.interfaces.IUsuarioRepository;
import br.upe.projetoAcademiaP2.data.repository.UsuarioCsvRepository;

import java.util.ArrayList;
import java.util.List;

public class UsuarioBusiness {

    private IUsuarioRepository usuarioRepository;

    public UsuarioBusiness() {
        this.usuarioRepository = new UsuarioCsvRepository();
    }

    public String autenticar(String email, String senha) {
        Usuario usuario = usuarioRepository.findByEmail(email);

        if (usuario != null && usuario.getSenha().equals(senha)) {
            if (usuario instanceof Adm) { 
                return "ADM";
            } else if (usuario instanceof Comum) {
                return "COMUM";
            }
        }
        return null; 
    }

    public void cadastrarUsuario(Usuario usuario) {
        usuarioRepository.create(usuario);
        System.out.println("Usuário cadastrado com sucesso!");
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.listarTodos();
    }

    public List<Comum> listarUsuariosComuns() {
        List<Comum> comuns = new ArrayList<>();
        for (Usuario u : usuarioRepository.listarTodos()) {
            if (u instanceof Comum) {
                comuns.add((Comum) u);
            }
        }
        return comuns;
    }

    public void deletarUsuario(String email) {
        boolean deletado = usuarioRepository.delete(email);
        if (deletado) {
            System.out.println("Usuário removido com sucesso!");
        } else {
            System.out.println("Usuário não encontrado.");
        }
    }
    
    public void atualizarUsuario(Usuario usuario) {
        Usuario atualizado = usuarioRepository.update(usuario);
        if (atualizado != null) {
            System.out.println("Dados do usuário atualizados com sucesso!");
        } else {
            System.out.println("Falha ao atualizar: usuário não encontrado.");
        }
    }
}
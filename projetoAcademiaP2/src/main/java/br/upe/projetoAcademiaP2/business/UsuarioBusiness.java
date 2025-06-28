package br.upe.projetoAcademiaP2.business;

import br.upe.projetoAcademiaP2.beans.Administrador;
import br.upe.projetoAcademiaP2.beans.Comum;
import br.upe.projetoAcademiaP2.beans.Usuario;
import br.upe.projetoAcademiaP2.repository.IUsuarioRepository;
import br.upe.projetoAcademiaP2.repository.UsuarioRepoImpl;

import java.util.List;

public class UsuarioBusiness {

    private IUsuarioRepository usuarioRepository;

    public UsuarioBusiness() {
        this.usuarioRepository = new UsuarioRepoImpl();
    }

    public String autenticar(String email, String senha) {
        Usuario usuario = usuarioRepository.buscarPorEmail(email);

        if (usuario != null && usuario.getSenha().equals(senha)) {
            if (usuario instanceof Administrador) {
                return "ADM";
            } else if (usuario instanceof Comum) {
                return "ALUNO";
            }
        }
        return null; 
    }

    public void cadastrarUsuario(Usuario usuario) {
        usuarioRepository.salvar(usuario);
        System.out.println("Usuário cadastrado com sucesso!");
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.listarTodos();
    }

    public void deletarUsuario(String email) {
        Usuario usuario = usuarioRepository.buscarPorEmail(email);
        if (usuario != null) {
            usuarioRepository.deletar(usuario);
            System.out.println("Usuário removido com sucesso!");
        } else {
            System.out.println("Usuário não encontrado.");
        }
    }
    
    public void atualizarUsuario(Usuario usuario) {
        usuarioRepository.salvar(usuario);
        System.out.println("Dados do usuário atualizados com sucesso!");
    }
}
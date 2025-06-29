package br.upe.projetoAcademiaP2.business;

import br.upe.projetoAcademiaP2.data.beans.Adm;
import br.upe.projetoAcademiaP2.data.beans.Comum;
import br.upe.projetoAcademiaP2.data.beans.Usuario;
import br.upe.projetoAcademiaP2.data.repository.interfaces.IUsuarioRepository;
import br.upe.projetoAcademiaP2.data.repository.UsuarioRepository;

import java.util.List;

public class UsuarioBusiness {

    private IUsuarioRepository usuarioRepository;

    public UsuarioBusiness() {
        this.usuarioRepository = new UsuarioRepository();
    }

    public String autenticar(String email, String senha) {
        // A lógica de autenticação agora funciona pois Usuario tem getSenha()
        Usuario usuario = usuarioRepository.findByEmail(email);

        if (usuario != null && usuario.getSenha().equals(senha)) {
            if (usuario instanceof Adm) { // ALTERADO: Corrigido para a classe Adm
                return "ADM";
            } else if (usuario instanceof Comum) {
                return "ALUNO";
            }
        }
        return null; 
    }

    public void cadastrarUsuario(Usuario usuario) {
        // ALTERADO: Chamando o método 'create' que existe no repositório
        usuarioRepository.create(usuario);
        System.out.println("Usuário cadastrado com sucesso!");
    }

    public List<Usuario> listarUsuarios() {
        // ALTERADO: Chamando o método 'listarTodos' que agora existe
        return usuarioRepository.listarTodos();
    }

    public void deletarUsuario(String email) {
        // ALTERADO: Lógica simplificada e chamada correta ao método 'delete'
        boolean deletado = usuarioRepository.delete(email);
        if (deletado) {
            System.out.println("Usuário removido com sucesso!");
        } else {
            System.out.println("Usuário não encontrado.");
        }
    }
    
    public void atualizarUsuario(Usuario usuario) {
        // ALTERADO: Chamando o método 'update' que existe no repositório
        Usuario atualizado = usuarioRepository.update(usuario);
        if (atualizado != null) {
            System.out.println("Dados do usuário atualizados com sucesso!");
        } else {
            System.out.println("Falha ao atualizar: usuário não encontrado.");
        }
    }
}
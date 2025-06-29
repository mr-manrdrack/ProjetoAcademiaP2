package br.upe.projetoAcademiaP2.data.repository;

import br.upe.projetoAcademiaP2.data.beans.Usuario;
import br.upe.projetoAcademiaP2.data.repository.interfaces.IUsuarioRepository;

import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository implements IUsuarioRepository {

    private List<Usuario> usuarios = new ArrayList<>();

    @Override
    public Usuario create(Usuario usuario) {
        // Para evitar duplicatas, podemos verificar se o usuário já existe
        if (findByEmail(usuario.getEmail()) == null) {
            usuarios.add(usuario);
            return usuario;
        }
        return null; // Retorna null se o usuário já existe
    }

    @Override
    public Usuario findByEmail(String email) {
        for (Usuario u : usuarios) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                return u;
            }
        }
        return null;
    }
    
    // NOVO: Implementação do método listarTodos
    @Override
    public List<Usuario> listarTodos() {
        return new ArrayList<>(usuarios); // Retorna uma cópia para proteger a lista original
    }

    @Override
    public boolean delete(String email) {
        Usuario encontrado = findByEmail(email);
        if (encontrado != null) {
            return usuarios.remove(encontrado);
        }
        return false;
    }

    @Override
    public Usuario update(Usuario usuario) {
        Usuario existente = findByEmail(usuario.getEmail());

        if (existente != null) {
            existente.setNome(usuario.getNome());
            existente.setTelefone(usuario.getTelefone());
            existente.setSenha(usuario.getSenha()); // Importante atualizar a senha também
            existente.setPesoAtual(usuario.getPesoAtual());
            existente.setAlturaAtual(usuario.getAlturaAtual());
            existente.setPercGorduraAtual(usuario.getPercGorduraAtual());
            existente.setPlanTreinos(usuario.getPlanTreinos());
            existente.setIndicaBio(usuario.getIndicaBio());
            return existente;
        }

        return null;
    }
}
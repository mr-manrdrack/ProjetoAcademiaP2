package br.upe.projetoAcademiaP2.data.repository;

import br.upe.projetoAcademiaP2.data.beans.Usuario;
import br.upe.projetoAcademiaP2.data.repository.interfaces.IUsuarioRepository;

import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository implements IUsuarioRepository {

    private List<Usuario> usuarios = new ArrayList<>();

    @Override
    public Usuario create(Usuario usuario) {
        usuarios.add(usuario);
        return usuario;
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


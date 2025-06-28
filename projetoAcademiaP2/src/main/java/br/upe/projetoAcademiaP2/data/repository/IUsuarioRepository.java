package br.upe.projetoAcademiaP2.data.repository;

import br.upe.projetoAcademiaP2.data.beans.Usuario;

public interface IUsuarioRepository {
    Usuario create(Usuario u);
    Usuario findByEmail(String email);
    boolean delete(String email);
    boolean update(Usuario u);
}

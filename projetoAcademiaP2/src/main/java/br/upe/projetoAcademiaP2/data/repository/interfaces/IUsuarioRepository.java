package br.upe.projetoAcademiaP2.data.repository.interfaces;

import br.upe.projetoAcademiaP2.data.beans.Usuario;
import java.util.List; 

public interface IUsuarioRepository {
    Usuario create(Usuario u);
    Usuario findByEmail(String email);
    Usuario update(Usuario u);
    boolean delete(String email);
    
    List<Usuario> listarTodos();
}

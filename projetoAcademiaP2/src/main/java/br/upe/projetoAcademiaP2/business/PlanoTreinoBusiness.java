package br.upe.projetoAcademiaP2.business;

import br.upe.projetoAcademiaP2.data.beans.PlanoTreino;
import br.upe.projetoAcademiaP2.data.beans.Usuario;
import br.upe.projetoAcademiaP2.data.repository.interfaces.IUsuarioRepository;
import br.upe.projetoAcademiaP2.data.repository.UsuarioCsvRepository; 

import java.util.List;

public class PlanoTreinoBusiness {

    
    private IUsuarioRepository usuarioRepository;

    public PlanoTreinoBusiness() {
        
        this.usuarioRepository = new UsuarioCsvRepository();
    }

    
    public void cadastrarPlanoDeTreino(Usuario usuario, PlanoTreino plano) {
        if (usuario == null || plano == null) {
            System.err.println("Usuário ou Plano de Treino nulos.");
            return;
        }
        
        usuario.addPlanoTreino(plano);
        
        
        usuarioRepository.update(usuario);
        
        System.out.println("Plano de treino '" + plano.getNomePlano() + "' cadastrado para o usuário " + usuario.getNome());
    }

    public List<PlanoTreino> visualizarPlanosDeTreino(Usuario usuario) {
        return usuario.getPlanTreinos();
    }
    
    
    public void modificarPlanoDeTreino(Usuario usuario) {
         if (usuario == null) {
            System.err.println("Usuário nulo, não é possível salvar as modificações do plano.");
            return;
        }
        usuarioRepository.update(usuario);
        System.out.println("Plano de treino do usuário " + usuario.getNome() + " atualizado com sucesso!");
    }
}
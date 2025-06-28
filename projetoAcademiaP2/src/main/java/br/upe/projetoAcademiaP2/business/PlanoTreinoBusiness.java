package br.upe.projetoAcademiaP2.business;

import br.upe.projetoAcademiaP2.beans.PlanoTreino;
import br.upe.projetoAcademiaP2.beans.Usuario;
import br.upe.projetoAcademiaP2.repository.IPlanoTreinoRepository;
import br.upe.projetoAcademiaP2.repository.PlanoTreinoRepoImpl;

public class PlanoTreinoBusiness {

    private IPlanoTreinoRepository planoTreinoRepository;


    public PlanoTreinoBusiness() {
        this.planoTreinoRepository = new PlanoTreinoRepoImpl();
    }

    public void cadastrarPlanoDeTreino(Usuario usuario, PlanoTreino plano) {
        usuario.setPlanoTreino(plano);
        planoTreinoRepository.salvar(plano);
        System.out.println("Plano de treino cadastrado para " + usuario.getNome());
    }

    public PlanoTreino visualizarPlanoDeTreino(Usuario usuario) {
        return planoTreinoRepository.buscarPlanoPorUsuario(usuario);
    }
    
    public void modificarPlanoDeTreino(PlanoTreino plano) {
        planoTreinoRepository.salvar(plano);
        System.out.println("Plano de treino modificado com sucesso!");
    }
}
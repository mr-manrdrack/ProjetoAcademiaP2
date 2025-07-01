package br.upe.projetoAcademiaP2.business;

import br.upe.projetoAcademiaP2.data.beans.PlanoTreino;
import br.upe.projetoAcademiaP2.data.beans.Usuario;
import br.upe.projetoAcademiaP2.data.repository.UsuarioCsvRepository;
import br.upe.projetoAcademiaP2.data.repository.PlanoTreinoCsvRepository;
import br.upe.projetoAcademiaP2.data.repository.interfaces.IUsuarioRepository;

import java.util.List;

public class PlanoTreinoBusiness {

    private final IUsuarioRepository usuarioRepository;
    private final PlanoTreinoCsvRepository planoRepository;

    public PlanoTreinoBusiness() {
        this.usuarioRepository = new UsuarioCsvRepository();
        this.planoRepository = new PlanoTreinoCsvRepository();
    }

    public void cadastrarPlanoDeTreino(Usuario usuario, PlanoTreino plano) {
        if (usuario == null || plano == null) {
            System.err.println("Usuário ou Plano de Treino nulos.");
            return;
        }

        plano.setUsuario(usuario);
        usuario.setPlanTreinos(List.of(plano));

        usuarioRepository.update(usuario);
        planoRepository.salvarPlano(plano);

        System.out.println("Plano de treino '" + plano.getNomePlano() + "' cadastrado para o usuário " + usuario.getNome());
    }

    public PlanoTreino carregarPlanoDoUsuario(Usuario usuario) {
        if (usuario == null) {
            System.err.println("Usuário nulo.");
            return null;
        }

        PlanoTreino plano = planoRepository.carregarPlano(usuario);

        if (plano != null) {
            plano.setUsuario(usuario);
        }

        return plano;
    }

    public void modificarPlanoDeTreino(PlanoTreino plano) {
        if (plano == null || plano.getUsuario() == null) {
            System.err.println("Plano ou usuário nulos.");
            return;
        }

        planoRepository.salvarPlano(plano);
        System.out.println("Plano de treino atualizado com sucesso!");
    }

    public void exibirPlanoDeTreino(PlanoTreino plano) {
        System.out.println("Plano: " + plano.getNomePlano());

        for (var secao : plano.getSecoes()) {
            System.out.println("  ➤ Seção: " + secao.getNomeTreino());
            for (var item : secao.getItensPlano()) {
                System.out.printf("     - %s: %d séries x %d reps (carga: %dkg)%n",
                        item.getExercicio().getNome(),
                        item.getSeries(),
                        item.getRepeticoes(),
                        item.getCarga());
            }
        }
    }
}

package br.upe.projetoAcademiaP2.business;

import br.upe.projetoAcademiaP2.beans.ItemPlanoTreino;
import br.upe.projetoAcademiaP2.beans.PlanoTreino;

public class SecaoTreinoBusiness {

    private PlanoTreinoBusiness planoTreinoBusiness;

    public SecaoTreinoBusiness() {
        this.planoTreinoBusiness = new PlanoTreinoBusiness();
    }

    public void iniciarSessao(PlanoTreino plano) {
        System.out.println("Iniciando seção de treino para o plano: " + plano.getNome());
    }

    public void registrarPerformance(PlanoTreino plano, ItemPlanoTreino itemOriginal, double cargaRealizada, String repeticoesRealizadas) {
        System.out.println("Registrando performance para o exercício: " + itemOriginal.getExercicio().getNome());

        boolean mudouCarga = itemOriginal.getCargaPlanejada() != cargaRealizada;
        boolean mudouRepeticoes = !itemOriginal.getRepeticoesPlanejadas().equals(repeticoesRealizadas);

        if (mudouCarga || mudouRepeticoes) {
            System.out.println("Performance diferente do planejado!");
            itemOriginal.setCargaPlanejada(cargaRealizada);
            itemOriginal.setRepeticoesPlanejadas(repeticoesRealizadas);
            planoTreinoBusiness.modificarPlanoDeTreino(plano);
            System.out.println("Plano de treino atualizado com a nova performance.");
        } else {
            System.out.println("Performance conforme o planejado. Ótimo trabalho!");
        }
    }
}
package br.upe.projetoAcademiaP2.business;

import br.upe.projetoAcademiaP2.data.beans.ItemPlanoTreino;
import br.upe.projetoAcademiaP2.data.beans.PlanoTreino;
import br.upe.projetoAcademiaP2.data.beans.Usuario;

public class SecaoTreinoBusiness {

    private PlanoTreinoBusiness planoTreinoBusiness;

    public SecaoTreinoBusiness() {
        this.planoTreinoBusiness = new PlanoTreinoBusiness();
    }

    public void iniciarSessao(PlanoTreino plano) {
        if (plano != null) {
            System.out.println("Iniciando seção de treino para o plano: " + plano.getNomePlano());
        }
    }

    // ALTERADO: A assinatura do método foi corrigida para usar os tipos corretos (int).
    public void registrarPerformance(Usuario usuario, PlanoTreino plano, ItemPlanoTreino itemOriginal, int cargaRealizada, int repeticoesRealizadas, int seriesRealizadas) {
        System.out.println("Registrando performance para o exercício: " + itemOriginal.getExercicio().getNome());

        // ALTERADO: Adicionada verificação de mudança nas séries.
        boolean mudouCarga = itemOriginal.getCarga() != cargaRealizada;
        boolean mudouRepeticoes = itemOriginal.getRepeticoes() != repeticoesRealizadas;
        boolean mudouSeries = itemOriginal.getSeries() != seriesRealizadas;

        if (mudouCarga || mudouRepeticoes || mudouSeries) {
            System.out.println("Performance diferente do planejado!");

            // A alteração é feita diretamente no objeto em memória.
            // ALTERADO: Adicionada a atualização das séries no item.
            itemOriginal.setCarga(cargaRealizada);
            itemOriginal.setRepeticoes(repeticoesRealizadas);
            itemOriginal.setSeries(seriesRealizadas);

            // Em seguida, passamos o plano modificado para o business service salvá-lo.
            // A sua classe PlanoTreinoBusiness já espera receber o objeto PlanoTreino.
            planoTreinoBusiness.modificarPlanoDeTreino(plano);
            System.out.println("Plano de treino atualizado para refletir a nova performance.");

        } else {
            System.out.println("Performance conforme o planejado! Ótimo trabalho!");
        }
    }
}
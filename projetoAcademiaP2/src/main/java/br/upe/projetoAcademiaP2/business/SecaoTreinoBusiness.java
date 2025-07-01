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

    public void registrarPerformance(ItemPlanoTreino itemOriginal, int cargaRealizada, int repeticoesRealizadas, int seriesRealizadas) {
        System.out.println("Atualizando dados do exercício '" + itemOriginal.getExercicio().getNome() + "' em memória.");
        
        itemOriginal.setCarga(cargaRealizada);
        itemOriginal.setRepeticoes(repeticoesRealizadas);
        itemOriginal.setSeries(seriesRealizadas);
    }
}
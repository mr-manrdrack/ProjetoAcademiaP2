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
    public void registrarPerformance(Usuario usuario, PlanoTreino plano, ItemPlanoTreino itemOriginal, int cargaRealizada, int repeticoesRealizadas) {
        System.out.println("Registrando performance para o exercício: " + itemOriginal.getExercicio().getNome());

        // ALTERADO: Comparação corrigida para tipos primitivos (int).
        boolean mudouCarga = itemOriginal.getCarga() != cargaRealizada;
        boolean mudouRepeticoes = itemOriginal.getRepeticoes() != repeticoesRealizadas;

        if (mudouCarga || mudouRepeticoes) {
            System.out.println("Performance diferente do planejado!");

            // A alteração é feita diretamente no objeto em memória.
            // ALTERADO: Chamadas de método corrigidas para os nomes corretos da classe ItemPlanoTreino.
            itemOriginal.setCarga(cargaRealizada);
            itemOriginal.setRepeticoes(repeticoesRealizadas);

            // Em seguida, passamos o USUÁRIO para que o business service possa salvá-lo.
            planoTreinoBusiness.modificarPlanoDeTreino(usuario);
            System.out.println("Plano de treino atualizado para refletir a nova performance.");

        } else {
            System.out.println("Performance conforme o planejado. Ótimo trabalho!");
        }
    }
}
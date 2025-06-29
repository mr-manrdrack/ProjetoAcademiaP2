package br.upe.projetoAcademiaP2.business;

public class ExercicioBusiness{
    private IExercicioRepository exercicioRepo;

    public ExercicioBusiness(IExercicioRepository exercicioRepo) {
        this.exercicioRepo = exercicioRepo;
    }

    public IExercicioRepository listarTodos() {
        return exercicioRepo;
    }
    public void salvar(Exercicio E){
        this.exercicioRepo.setExercicio(E);
    }

}
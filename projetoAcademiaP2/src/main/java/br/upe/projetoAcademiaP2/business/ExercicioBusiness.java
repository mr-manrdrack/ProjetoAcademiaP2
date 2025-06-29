package br.upe.projetoAcademiaP2.business;
import br.upe.projetoAcademiaP2.repository.interfaces.IExercicioRepository;
import java.util.List;

public class ExercicioBusiness{
    private IExercicioRepository exercicioRepo;

    public ExercicioBusiness(IExercicioRepository exercicioRepo) {
        this.exercicioRepo = exercicioRepo;
    }

    public List<Exercicio> listarTodos() {
        return exercicioRepo.findAll();
    }

    public Exercicio salvar(Exercicio E){
        return exercicioRepo.create(E);
    }

}
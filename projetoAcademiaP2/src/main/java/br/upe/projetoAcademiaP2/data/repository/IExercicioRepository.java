package data.repository.interfaces;

import data.beans.Exercicio;
import java.util.List;

public interface IExercicioRepository {
    Exercicio create(Exercicio e);
    Exercicio findByNome(String nome);
    List<Exercicio> findAll();
}


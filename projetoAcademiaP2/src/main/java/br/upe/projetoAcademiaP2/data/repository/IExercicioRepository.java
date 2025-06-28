package br.upe.projetoAcademiaP2.data.repository.interfaces;

import br.upe.projetoAcademiaP2.data.beans.Exercicio;
import java.util.List;

public interface IExercicioRepository {
    Exercicio create(Exercicio e);
    Exercicio findByNome(String nome);
    List<Exercicio> findAll();
    boolean delete(String nome);
}


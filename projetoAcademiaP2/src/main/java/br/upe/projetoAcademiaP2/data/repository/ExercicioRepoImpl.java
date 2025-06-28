package br.upe.projetoAcademiaP2.data.repository;

import br.upe.projetoAcademiaP2.data.beans.Exercicio;
import br.upe.projetoAcademiaP2.data.repository.interfaces.IExercicioRepository;

import java.util.ArrayList;
import java.util.List;

public class ExercicioRepository implements IExercicioRepository {

    private List<Exercicio> exercicios = new ArrayList<>();

    @Override
    public Exercicio create(Exercicio exercicio) {
        exercicios.add(exercicio);
        return exercicio;
    }

    @Override
    public List<Exercicio> findAll() {
        return new ArrayList<>(exercicios);
    }

    @Override
    public Exercicio findByNome(String nome) {
        for (Exercicio e : exercicios) {
            if (e.getNome().equalsIgnoreCase(nome)) {
                return e;
            }
        }
        return null;
    }

    @Override
    public Exercicio update(Exercicio e) {
        Exercicio existente = findByNome(e.getNome());
        if (existente != null) {
            existente.setDescricao(e.getDescricao());
            existente.setGif(e.getGif());
            return existente;
        }
        return null;
    }

    @Override
    public boolean delete(String nome) {
        Exercicio exercicio = findByNome(nome);
        if (exercicio != null) {
            return exercicios.remove(exercicio);
        }
        return false;
    }
}



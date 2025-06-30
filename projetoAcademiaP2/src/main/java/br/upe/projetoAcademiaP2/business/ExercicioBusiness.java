package br.upe.projetoAcademiaP2.business;

import br.upe.projetoAcademiaP2.data.beans.Exercicio;
import br.upe.projetoAcademiaP2.data.repository.ExercicioRepoImpl;
import br.upe.projetoAcademiaP2.data.repository.interfaces.IExercicioRepository;
import java.util.List;

public class ExercicioBusiness {
    private IExercicioRepository exercicioRepository;

    public ExercicioBusiness() {
        this.exercicioRepository = new ExercicioRepoImpl();
    }

    public void salvar(Exercicio exercicio) {
        if (exercicio == null || exercicio.getNome() == null || exercicio.getNome().trim().isEmpty()) {
            System.err.println("Exercício inválido para cadastro.");
            return;
        }

        Exercicio existente = exercicioRepository.findByNome(exercicio.getNome());
        if (existente != null) {
            System.out.println("Exercício com este nome já existe.");
            return;
        }

        Exercicio exercicioCriado = exercicioRepository.create(exercicio);
        if (exercicioCriado != null) {
            System.out.println("Exercício '" + exercicio.getNome() + "' cadastrado com sucesso!");
        } else {
            System.out.println("Erro ao cadastrar exercício.");
        }
    }

    public List<Exercicio> listarExercicios() {
        return exercicioRepository.findAll();
    }

    public Exercicio buscarExercicioPorNome(String nome) {
        return exercicioRepository.findByNome(nome);
    }

    public void atualizarExercicio(Exercicio exercicio) {
        if (exercicio == null || exercicio.getNome() == null) {
            System.err.println("Exercício inválido para atualização.");
            return;
        }

        Exercicio atualizado = exercicioRepository.update(exercicio);
        if (atualizado != null) {
            System.out.println("Exercício atualizado com sucesso!");
        } else {
            System.out.println("Exercício não encontrado para atualização.");
        }
    }

    public void deletarExercicio(String nome) {
        boolean deletado = exercicioRepository.delete(nome);
        if (deletado) {
            System.out.println("Exercício removido com sucesso!");
        } else {
            System.out.println("Exercício não encontrado para remoção.");
        }
    }

    public void cadastrarExercicio(Exercicio exercicio) {
        salvar(exercicio);
    }
}
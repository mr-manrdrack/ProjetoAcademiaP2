package br.upe.projetoAcademiaP2.business;

import br.upe.projetoAcademiaP2.data.beans.Exercicio;
import br.upe.projetoAcademiaP2.data.repository.ExercicioRepoImpl;
import br.upe.projetoAcademiaP2.data.repository.interfaces.IExercicioRepository;

import java.util.InputMismatchException;
import java.util.List;

public class ExercicioBusiness {
    private IExercicioRepository exercicioRepository;
    private ExercicioBusiness exercicioBusiness;

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
        try {
            if(exercicio == null){
                throw new InputMismatchException();
            }
            exercicioRepository.update(exercicio);
        } catch (InputMismatchException IME){
            System.out.println("Algum campo acabou ficando em branco, tente novamente");
        } catch (Exception e) {
            System.out.println("Algo deu errado. Por favor, tente novamente");
        }
    }

    public void deletarExercicio(String nome) {
        try{
            if (nome.isEmpty() || nome.equals(" ")){
                throw new InputMismatchException();
            }
            exercicioRepository.delete(nome);
        }catch (InputMismatchException IME){
            System.out.println("nome vazio, por favor escreva novamente");
        }
        catch (Exception e) {
            System.out.println("Algo deu errado. Por favor, tente novamente");
        }
    }
}
package br.upe.projetoAcademiaP2.data.beans;

public class ItemPlanoTreino {

    private Exercicio exercicio;
    private int series;
    private int repeticoes;
    private int carga;



    public ItemPlanoTreino(Exercicio exercicio, int series, int repeticoes, int carga) {
        this.exercicio = exercicio;
        this.series = series;
        this.repeticoes = repeticoes;
        this.carga = carga;
    }

    public Exercicio getExercicio() {
        return exercicio;
    }

    public void setExercicio(Exercicio exercicio) {
        this.exercicio = exercicio;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public int getRepeticoes() {
        return repeticoes;
    }

    public void setRepeticoes(int repeticoes) {
        this.repeticoes = repeticoes;
    }

    public int getCarga() {
        return carga;
    }

    public void setCarga(int carga) {
        this.carga = carga;
    }
}
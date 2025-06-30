package br.upe.projetoAcademiaP2.data.beans;

public class Exercicio {

    private String nome;
    private String descricao;
    private String caminhoGif;


    public Exercicio(String nome, String descricao, String caminhoGif) {
        this.nome = nome;
        this.descricao = descricao;
        this.caminhoGif = caminhoGif;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCaminhoGif() {
        return caminhoGif;
    }

    public void setCaminhoGif(String caminhoGif) {
        this.caminhoGif = caminhoGif;
    }
}
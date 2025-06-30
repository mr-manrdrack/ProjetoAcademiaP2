package br.upe.projetoAcademiaP2.data.beans;

public class Adm extends Usuario {



    public Adm(String nome, String telefone, String email, String senha, Double pesoAtual, Double alturaAtual, Double percGorduraAtual) {

        super(nome, telefone, email, senha, pesoAtual, alturaAtual, percGorduraAtual);
    }

    public Adm() {
        super();
    }
}
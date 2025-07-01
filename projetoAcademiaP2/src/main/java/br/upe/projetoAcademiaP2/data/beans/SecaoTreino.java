package br.upe.projetoAcademiaP2.data.beans;

import java.util.ArrayList;
import java.util.List;

public class SecaoTreino {
    private String id;
    private String nomeTreino; //peito, perna...
    private PlanoTreino planoTreino;
    private List<ItemPlanoTreino> itensPlano;



    public SecaoTreino(String id, String nomeTreino, PlanoTreino planoTreino){
        this.id = id;
        this.nomeTreino = nomeTreino;
        this.planoTreino = planoTreino;
        this.itensPlano = new ArrayList<>();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNomeTreino() { return nomeTreino; }
    public void setNomeTreino(String nomeTreino) { this.nomeTreino = nomeTreino; }
    public PlanoTreino getPlanoTreino() { return planoTreino; }
    public void setPlanoTreino(PlanoTreino planoTreino) { this.planoTreino = planoTreino; }
    public List<ItemPlanoTreino> getItensPlano() { return itensPlano; }

    public void setItensPlano(List<ItemPlanoTreino> itensPlano) {
        this.itensPlano = itensPlano;
    }

    public void addItemSecao(ItemPlanoTreino item) {
        this.itensPlano.add(item);
    }
}
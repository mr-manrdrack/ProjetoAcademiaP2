package br.upe.projetoAcademiaP2.data.beans;

import java.util.ArrayList;
import java.util.List;

public class SecaoTreino {
    private String id;
    private String nomeTreino; //peito, perna...
    private PlanoTreino planoTreino;
    private List<ItemPlanoTreino> itensPlano;

    public SecaoTreino(){
        this.itensPlano = new ArrayList<>();
    }

    public SecaoTreino(String id, String nomeTreino, PlanoTreino planoTreino){
        this(); // Chama o construtor padrão para inicializar a lista
        this.id = id;
        this.nomeTreino = nomeTreino;
        this.planoTreino = planoTreino;
    }

    // Getters e Setters...
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNomeTreino() { return nomeTreino; }
    public void setNomeTreino(String nomeTreino) { this.nomeTreino = nomeTreino; }
    public PlanoTreino getPlanoTreino() { return planoTreino; }
    public void setPlanoTreino(PlanoTreino planoTreino) { this.planoTreino = planoTreino; }
    public List<ItemPlanoTreino> getItensPlano() { return itensPlano; }

    // ALTERADO: Nome do método corrigido para consistência
    public void setItensPlano(List<ItemPlanoTreino> itensPlano) {
        this.itensPlano = itensPlano;
    }

    public void addItemSecao(ItemPlanoTreino item) {
        // ALTERADO: Verificação de nulidade removida por ser redundante,
        // já que os construtores já inicializam a lista.
        this.itensPlano.add(item);
    }
}
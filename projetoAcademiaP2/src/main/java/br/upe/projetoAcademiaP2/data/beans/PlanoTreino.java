package br.upe.projetoAcademiaP2.data.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PlanoDeTreino {

    private int id;
    private String nomePlano;
    private Date inicioPlano;
    private Date fimPlano;
    private Usuario usuario;
    private List<ItemPlanoTreino> itens;

    public PlanoDeTreino() {
        this.itens = new ArrayList<>();
    }

    public PlanoDeTreino(int id, String nomePlano, Date inicioPlano, Date fimPlano, Usuario usuario) {
        this.id = id;
        this.nomePlano = nomePlano;
        this.inicioPlano = inicioPlano;
        this.fimPlano = fimPlano;
        this.usuario = usuario;
        this.itens = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomePlano() {
        return nomePlano;
    }

    public void setNomePlano(String nomePlano) {
        this.nomePlano = nomePlano;
    }

    public Date getInicioPlano() {
        return inicioPlano;
    }

    public void setInicioPlano(Date inicioPlano) {
        this.inicioPlano = inicioPlano;
    }

    public Date getFimPlano() {
        return fimPlano;
    }

    public void setFimPlano(Date fimPlano) {
        this.fimPlano = fimPlano;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<ItemPlanoTreino> getItens() {
        return itens;
    }

    public void setItens(List<ItemPlanoTreino> itens) {
        this.itens = itens;
    }

    public void adicionarItem(ItemPlanoTreino item) {
        this.itens.add(item);
    }

    public ItemPlanoTreino getItemPorExercicio(Exercicio exercicio) {
        for (ItemPlanoTreino item : itens) {
            if (item.getExercicio().getNome().equalsIgnoreCase(exercicio.getNome())) {
                return item;
            }
        }
        return null;
    }
}


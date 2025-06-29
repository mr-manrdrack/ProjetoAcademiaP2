package br.upe.projetoAcademiaP2.data.beans;

import java.util.ArrayList;
import java.util.List;

public abstract class Usuario {
    private String nome;
    private String telefone;
    private String email;
    private Double pesoAtual;
    private Double alturaAtual;
    private Double percGorduraAtual;

    private List<PlanoTreino> planTreinos;
    private List<IndicadorBiomedico> indicaBio;

    public Usuario(){
        this.planTreinos = new ArrayList<>();
        this.indicaBio = new ArrayList<>();
    }

    public Usuario(String nome, String telefone, String email, Double pesoAtual, Double alturaAtual, Double percGorduraAtual){
        // Construtor completo
        this(); // Chama o construtor padrão para inicializar as listas
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.pesoAtual = pesoAtual;
        this.alturaAtual = alturaAtual;
        this.percGorduraAtual = percGorduraAtual;
    }
    
    // NOVO: Método de ajuda para adicionar um plano de forma segura
    public void addPlanoTreino(PlanoTreino plano) {
        if (this.planTreinos == null) {
            this.planTreinos = new ArrayList<>();
        }
        this.planTreinos.add(plano);
    }
    
    // ... Getters e Setters existentes ...
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Double getPesoAtual() { return pesoAtual; }
    public void setPesoAtual(Double pesoAtual) { this.pesoAtual = pesoAtual; }
    public Double getAlturaAtual() { return alturaAtual; }
    public void setAlturaAtual(Double alturaAtual) { this.alturaAtual = alturaAtual; }
    public Double getPercGorduraAtual() { return percGorduraAtual; }
    public void setPercGorduraAtual(Double percGorduraAtual) { this.percGorduraAtual = percGorduraAtual; }
    public List<PlanoTreino> getPlanTreinos() { return planTreinos; }
    public void setPlanTreinos(List<PlanoTreino> planTreinos) { this.planTreinos = planTreinos; }
    public List<IndicadorBiomedico> getIndicaBio() { return indicaBio; }
    public void setIndicaBio(List<IndicadorBiomedico> indicaBio) { this.indicaBio = indicaBio; }
}
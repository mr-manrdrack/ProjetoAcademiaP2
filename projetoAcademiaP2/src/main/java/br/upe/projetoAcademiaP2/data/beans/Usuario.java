package br.upe.projetoAcademiaP2.data.beans;

import java.util.ArrayList;
import java.util.List;

public abstract class Usuario {
    private String nome;
    private String telefone;
    private String email;
    private String senha;
    private Double pesoAtual;
    private Double alturaAtual;
    private Double percGorduraAtual;

    private List<PlanoTreino> planTreinos;
    private List<IndicadorBiomedico> indicaBio;


    public Usuario(String nome, String telefone, String email, String senha, Double pesoAtual, Double alturaAtual, Double percGorduraAtual){
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.senha = senha;
        this.pesoAtual = pesoAtual;
        this.alturaAtual = alturaAtual;
        this.percGorduraAtual = percGorduraAtual;
        this.planTreinos = new ArrayList<>();
        this.indicaBio = new ArrayList<>();
    }
    
    
    public Usuario() {
        
    }


    public void addPlanoTreino(PlanoTreino plano) {
        if (this.planTreinos == null) {
            this.planTreinos = new ArrayList<>();
        }
        this.planTreinos.add(plano);
    }
    
    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
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
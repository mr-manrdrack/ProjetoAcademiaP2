package br.upe.projetoAcademiaP2.data.beans;

import java.util.Date;

public class IndicadorBiomedico {
    private String email;
    private Double peso;
    private Double altura;
    private Double percentualGordura;
    private Double percentualMassaMagra;
    private Double imc;
    private Date dataRegistro;

    public IndicadorBiomedico(String email, Double peso, Double altura, Double percentualGordura, Double percentualMassaMagra, Double imc, Date dataRegistro) {
        this.email = email;
        this.peso = peso;
        this.altura = altura;
        this.percentualGordura = percentualGordura;
        this.percentualMassaMagra = percentualMassaMagra;
        this.imc = imc;
        this.dataRegistro = dataRegistro;
    }

    // Getters e Setters

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Double getPeso() { return peso; }
    public void setPeso(Double peso) { this.peso = peso; }

    public Double getAltura() { return altura; }
    public void setAltura(Double altura) { this.altura = altura; }

    public Double getPercentualGordura() { return percentualGordura; }
    public void setPercentualGordura(Double percentualGordura) { this.percentualGordura = percentualGordura; }

    public Double getPercentualMassaMagra() { return percentualMassaMagra; }
    public void setPercentualMassaMagra(Double percentualMassaMagra) { this.percentualMassaMagra = percentualMassaMagra; }

    public Double getImc() { return imc; }
    public void setImc(Double imc) { this.imc = imc; }

    public Date getDataRegistro() { return dataRegistro; }
    public void setDataRegistro(Date dataRegistro) { this.dataRegistro = dataRegistro; }
}
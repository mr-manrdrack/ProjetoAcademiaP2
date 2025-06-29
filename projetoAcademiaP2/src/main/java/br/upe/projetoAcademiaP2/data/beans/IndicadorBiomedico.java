package br.upe.projetoAcademiaP2.data.beans;

public class IndicadorBiomedico {
   private String id;
   private Double peso;
   private Double altura;
   private Double percentualGordura;
   private Double percentualMassaMagra;
   private Double imc;

   public IndicadorBiomedico(){

   }

    public IndicadorBiomedico(String id, Double peso, Double altura, Double percentualGordura, Double percentualMassaMagra, Double imc) {
        this.id = id;
        this.peso = peso;
        this.altura = altura;
        this.percentualGordura = percentualGordura;
        this.percentualMassaMagra = percentualMassaMagra;
        this.imc = imc;
    }

    // Getters e Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Double getAltura() {
        return altura;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }

    public Double getPercentualGordura() {
        return percentualGordura;
    }

    public void setPercentualGordura(Double percentualGordura) {
        this.percentualGordura = percentualGordura;
    }

    public Double getPercentualMassaMagra() {
        return percentualMassaMagra;
    }

    public void setPercentualMassaMagra(Double percentualMassaMagra) {
        this.percentualMassaMagra = percentualMassaMagra;
    }

    public Double getImc(){

        return imc;
    }

    public void setImc(Double imc){
        this.imc = imc;
    }
}

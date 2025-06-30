package br.upe.projetoAcademiaP2.data.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PlanoTreino {

    private int id;
    private String nomePlano;
    private Date inicioPlano;
    private Date fimPlano;
    private Usuario usuario;
    
    // Esta lista agora será preenchida dinamicamente. Não precisamos mais do campo 'itens'.
    // private List<ItemPlanoTreino> itens; 

    private List<SecaoTreino> secoes;

    public PlanoTreino() {
        this.secoes = new ArrayList<>();
    }

    public PlanoTreino(int id, String nomePlano, Date inicioPlano, Date fimPlano, Usuario usuario) {
        this(); // Chama o construtor padrão para inicializar a lista de seções
        this.id = id;
        this.nomePlano = nomePlano;
        this.inicioPlano = inicioPlano;
        this.fimPlano = fimPlano;
        this.usuario = usuario;
    }

    // --- MÉTODO CORRIGIDO ---
    /**
     * Retorna uma lista unificada de TODOS os itens de treino,
     * percorrendo todas as seções do plano.
     * @return Uma lista completa de ItemPlanoTreino.
     */
    public List<ItemPlanoTreino> getItens() {
        List<ItemPlanoTreino> todosOsItens = new ArrayList<>();
        if (this.secoes != null) {
            for (SecaoTreino secao : this.secoes) {
                todosOsItens.addAll(secao.getItensPlano());
            }
        }
        return todosOsItens;
    }
    
    // O método setItens não faz mais sentido, pois a lista é dinâmica.
    // Podemos removê-lo ou deixá-lo para compatibilidade, mas ele não será usado no fluxo correto.
    public void setItens(List<ItemPlanoTreino> itens) {
        // Esta implementação agora seria complexa, pois teríamos que distribuir
        // os itens nas seções corretas. É melhor gerenciar os itens através das seções.
        System.err.println("Aviso: setItens não é suportado. Gerencie os itens através das seções.");
    }

    // O resto da classe permanece o mesmo, pois a lógica de seções está correta.
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNomePlano() { return nomePlano; }
    public void setNomePlano(String nomePlano) { this.nomePlano = nomePlano; }
    public Date getInicioPlano() { return inicioPlano; }
    public void setInicioPlano(Date inicioPlano) { this.inicioPlano = inicioPlano; }
    public Date getFimPlano() { return fimPlano; }
    public void setFimPlano(Date fimPlano) { this.fimPlano = fimPlano; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    
    public List<SecaoTreino> getSecoes() { return secoes; }
    public void setSecoes(List<SecaoTreino> secoes) { this.secoes = secoes; }

    public SecaoTreino getOuCriarSecao(String nomeSecao) {
        for (SecaoTreino secao : secoes) {
            if (secao.getNomeTreino().equalsIgnoreCase(nomeSecao)) {
                return secao;
            }
        }
    
        String novoId = "sec_" + System.currentTimeMillis(); // Gera um ID simples e único
        SecaoTreino nova = new SecaoTreino(novoId, nomeSecao, this); 
    
        secoes.add(nova);
        return nova;
    }
}
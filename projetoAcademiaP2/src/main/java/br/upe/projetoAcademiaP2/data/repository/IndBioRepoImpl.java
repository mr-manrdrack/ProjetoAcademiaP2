package br.upe.projetoAcademiaP2.data.repository;

import br.upe.projetoAcademiaP2.data.beans.IndicadorBiomedico;
import br.upe.projetoAcademiaP2.data.repository.interfaces.IIndBioRepository;

import java.util.*;


public class IndBioRepoImpl implements IIndBioRepository {

    private ArrayList<IndicadorBiomedico> indicadoresBiomedicos = new ArrayList<IndicadorBiomedico>();

    @Override
    public boolean save(IndicadorBiomedico indicadorBiomedico) {
        try{
            if(indicadorBiomedico == null){
                throw new Exception();
            }else {
                return indicadoresBiomedicos.add(indicadorBiomedico);
            }
        } catch (Exception e) {
            System.out.println("O método de salvar falhou");
        }
        return false;
    }

    @Override
    public List<IndicadorBiomedico> findAll() {
        return indicadoresBiomedicos;
    }

    @Override
    public boolean update(IndicadorBiomedico informacaoVelha,IndicadorBiomedico informacaoNova) {
        try {
            for(int index = 0; index < indicadoresBiomedicos.size(); index++){
                if(indicadoresBiomedicos.get(index) == informacaoVelha){
                    indicadoresBiomedicos.set(index,informacaoNova);
                }
            }
            return true;
        } catch (Exception e) {
            System.out.println("O método de update falhou");
        }
        return false;
    }

}


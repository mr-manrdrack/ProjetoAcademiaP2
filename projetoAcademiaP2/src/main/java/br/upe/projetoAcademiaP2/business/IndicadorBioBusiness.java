package br.upe.projetoAcademiaP2.business;
import br.upe.projetoAcademiaP2.data.beans.IndicadorBiomedico;
import br.upe.projetoAcademiaP2.data.beans.Usuario;
import br.upe.projetoAcademiaP2.data.repository.interfaces.IIndBioRepository;
import java.util.ArrayList;
import java.util.Date;


public class IndicadorBioBusiness{
    private UsuarioBusiness usuarioBusiness;
    private IIndBioRepository indBioRepository;

    public IndicadorBioBusiness(UsuarioBusiness UB, IIndBioRepository IBR){
        this.usuarioBusiness = UB;
        this.indBioRepository = IBR;
    }

    public IndicadorBiomedico registrarIndicador(Usuario U, IndicadorBiomedico IB){
        return indBioRepository.save(IB);
    }

    public ArrayList<IndicadorBiomedico> consultarHistorico(Usuario U){
        ArrayList<IndicadorBiomedico> resultado = new ArrayList<IndicadorBiomedico>();
        for(int index = 0; index < indBioRepository.findAll().size(); index++){
            if(indBioRepository.findAll().get(index).getId().equals(U.getNome()))
                resultado.add(indBioRepository.findAll().get(index));
        }
        return resultado;
    }

    public void importarIndicadoresDeCSV(String caminhoArquivo){

    }

    public void exportarRelatorioEvolucao(Usuario U, Date inicio,Date fim){

    }
}

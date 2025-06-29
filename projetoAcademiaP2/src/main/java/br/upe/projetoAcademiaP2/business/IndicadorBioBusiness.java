package br.upe.projetoAcademiaP2.business;
import br.upe.projetoAcademiaP2.data.repository.interfaces.IIndBioRepository
import java.util.ArrayList;

public class IndicadorBioBusiness{
    private UsuarioBusiness usuarioBusiness;
    private IIndicadorBioRepository indBioRepository;

    public IndicadorBioBusiness(UsuarioBusiness UB, IIndicadorBioRepository IBR){
        this.usuarioBusiness = UB;
        this.indBioRepository = IBR;
    }

    public IndicadorBiometrico registrarIndicador(Usuario U, IndicadorBiometrico IB){
        return indBioRepository.save(IB);
    }

    public ArrayList<IndicadorBiometrico> consultarHistorico(Usuario U){
        ArrayList<IndicadorBiometrico> resultado = new ArrayList<IndicadorBiometrico>;
        for(int index = 0; index < indBioRepository.findAll().size(); index++){
            if(indBioRepository.findAll().get(index).getId().equals(U.getNome()))
                resultado.add(indBioRepository.findAll().get(index));
        }
        return resultado;
    }

    public void importarIndicadoresDeCSV(String caminhoArquivo){

    }

    public void exportarRelatorioEvolucao(Usuario U,Date inicio,Date fim){

    }
}
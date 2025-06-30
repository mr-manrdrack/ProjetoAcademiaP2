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

    public boolean importarIndicadoresDeCSV(String caminhoArquivo){
        try {
            CSVManipBusiness fileManip = new CSVManipBusiness();
            ArrayList<String> arquivoParaImportar = fileManip.leitor(caminhoArquivo);
            for (String s : arquivoParaImportar) {
                System.out.println(s + "\n");
            }
            for(int index = 0; index < arquivoParaImportar.size(); index++){

                String linha = arquivoParaImportar.get(index);
                String[] informacoesSeparadas = linha.split(";");
                String id = informacoesSeparadas[0];
                Double peso = Double.parseDouble(informacoesSeparadas[1]);
                Double altura = Double.parseDouble(informacoesSeparadas[2]);
                Double percentualGordura = Double.parseDouble(informacoesSeparadas[3]);
                Double percentualMassaMagra = Double.parseDouble(informacoesSeparadas[4]);
                Double imc = Double.parseDouble(informacoesSeparadas[5]);
                IndicadorBiomedico indicadorImportado = new IndicadorBiomedico(id,peso,altura,percentualGordura,percentualMassaMagra,imc);
                indBioRepository.save(indicadorImportado);
                System.out.println("\nInformações importadas");
                return true;
            }
        } catch (Exception e) {
            System.out.println("\nAlgo deu errado. Por favor, tente novamente");
        }
        return false;
    }

    public void exportarRelatorioEvolucao(Usuario U, Date inicio,Date fim){

    }
}

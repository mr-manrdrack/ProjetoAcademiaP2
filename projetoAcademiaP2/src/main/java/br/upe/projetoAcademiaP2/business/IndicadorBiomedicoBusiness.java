package br.upe.projetoAcademiaP2.business;
import br.upe.projetoAcademiaP2.data.beans.IndicadorBiomedico;
import br.upe.projetoAcademiaP2.data.beans.Usuario;
import br.upe.projetoAcademiaP2.data.repository.interfaces.IIndBioRepository;
import java.util.ArrayList;
import java.util.Date;


public class IndicadorBiomedicoBusiness {
    private UsuarioBusiness usuarioBusiness;
    private IIndBioRepository indBioRepository;
    private CSVManipBusiness fileManip = new CSVManipBusiness();

    public IndicadorBiomedicoBusiness(UsuarioBusiness UB, IIndBioRepository IBR){
        this.usuarioBusiness = UB;
        this.indBioRepository = IBR;
    }

    public void cadastrarIndicador(Usuario usuario, IndicadorBiomedico indicador){
        if(usuario != null && indicador != null){
            indBioRepository.save(indicador);
        }
    }

    public ArrayList<IndicadorBiomedico> listarIndicadores(Usuario usuario){
        try {
            ArrayList<IndicadorBiomedico> resultado = new ArrayList<IndicadorBiomedico>();
            for (int index = 0; index < indBioRepository.findAll().size(); index++) {
                if(indBioRepository.findAll().get(index).getId().equals(usuario.getEmail())){
                    resultado.add(indBioRepository.findAll().get(index));
                }
            }
            return resultado;
        } catch (Exception e) {
            return null;
        }
    }

    public void atualizarIndicador(IndicadorBiomedico indicador){
        indBioRepository.update(indicador);
    }

    public boolean importarIndicadoresDeCSV(String caminhoArquivo){
        try {
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

    public boolean exportarRelatorioEvolucao(Usuario U, Date inicio,Date fim){
        try{
            String nome = U.getNome();
            String email = U.getEmail();
            ArrayList<String> stringParaExportacao = new ArrayList<String>();
            ArrayList<String> separado = new ArrayList<String>();
            for(int index = 0; index < indBioRepository.findAll().size(); index++){
                if(indBioRepository.findAll().get(index).getId().equals(email)){
                    separado.add(String.valueOf(indBioRepository.findAll().get(index).getPercentualGordura()));
                    separado.add(String.valueOf(indBioRepository.findAll().get(index).getPercentualMassaMagra()));
                    separado.add(String.valueOf(indBioRepository.findAll().get(index).getImc()));
                    separado.add(String.valueOf(inicio));
                    separado.add(String.valueOf(fim));
                }
            }
            for(int index = 0; index < separado.size(); index += 5){
                stringParaExportacao.add(separado.get(index) +","+ separado.get(index+1) +","+ separado.get(index+2) +","+ separado.get(index+3) +","+ separado.get(index+4));
            }
            ArrayList<String> nomeDosCampos = new ArrayList<String>();
            nomeDosCampos.add("Percentual de gordura");
            nomeDosCampos.add("Percentual de massa magra");
            nomeDosCampos.add("IMC");
            nomeDosCampos.add("Data de início");
            nomeDosCampos.add("Data de fim");

            fileManip.escritor(nomeDosCampos,stringParaExportacao,"RelatorioDeEvolucao.CSV","src/main/java/br/upe/projetoAcademiaP2/exported");
            return true;

        } catch (Exception e) {
            System.out.println("\nAlgo deu errado. Por favor, tente novamente");
        }
        return false;
    }
}
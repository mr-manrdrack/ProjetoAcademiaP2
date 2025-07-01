package br.upe.projetoAcademiaP2.business;

import br.upe.projetoAcademiaP2.data.beans.IndicadorBiomedico;
import br.upe.projetoAcademiaP2.data.beans.Usuario;
import br.upe.projetoAcademiaP2.data.repository.IndBioRepoImpl;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class IndicadorBiomedicoBusiness {
    private IndBioRepoImpl indBioRepository = new IndBioRepoImpl();
    private CSVManipBusiness fileManip = new CSVManipBusiness();
    private final String caminhoArquivo = "data/indicadores.csv";

    public IndicadorBiomedicoBusiness() {}

    public void cadastrarIndicador(Usuario usuario, IndicadorBiomedico indicador) {
        if (usuario != null && indicador != null) {
            indicador.setEmail(usuario.getEmail());
            indBioRepository.save(indicador);
            salvarNoCSV(indicador);
        }
    }

    public ArrayList<IndicadorBiomedico> listarIndicadores(Usuario usuario) {
        ArrayList<IndicadorBiomedico> resultado = new ArrayList<>();
        for (IndicadorBiomedico ind : indBioRepository.findAll()) {
            if (ind.getEmail().equals(usuario.getEmail())) {
                resultado.add(ind);
            }
        }
        return resultado;
    }

    private void salvarNoCSV(IndicadorBiomedico indicador) {
        try {
            FileWriter writer = new FileWriter(caminhoArquivo, true);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dataFormatada = sdf.format(indicador.getDataRegistro());
            writer.append(String.format(Locale.US,
                    "%s,%.2f,%.2f,%.2f,%.2f,%.2f,%s\n",
                    indicador.getEmail(),
                    indicador.getPeso(),
                    indicador.getAltura(),
                    indicador.getPercentualGordura(),
                    indicador.getPercentualMassaMagra(),
                    indicador.getImc(),
                    sdf.format(indicador.getDataRegistro())
            ));
            writer.close();
        } catch (IOException e) {
            System.out.println("Erro ao salvar no CSV: " + e.getMessage());
        }
    }

    public boolean importarIndicadoresDeCSV(Usuario usuario, String caminhoArquivo){
        try {
            ArrayList<String> arquivoParaImportar = fileManip.leitor(caminhoArquivo);
            for (String linha : arquivoParaImportar) {
                String[] dados = linha.split(",");
                String email = dados[0];
                double peso = Double.parseDouble(dados[1]);
                double altura = Double.parseDouble(dados[2]);
                double gordura = Double.parseDouble(dados[3]);
                double massa = Double.parseDouble(dados[4]);
                double imc = Double.parseDouble(dados[5]);
                Date dataRegistro = new Date();

                IndicadorBiomedico ind = new IndicadorBiomedico(email, peso, altura, gordura, massa, imc, dataRegistro);
                indBioRepository.save(ind);
            }
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao importar indicadores: " + e.getMessage());
            return false;
        }
    }

    public boolean exportarRelatorioEvolucao(Usuario U, Date inicio,Date fim){
        try{
            String nome = U.getNome();
            String email = U.getEmail();
            ArrayList<String> stringParaExportacao = new ArrayList<String>();
            ArrayList<String> separado = new ArrayList<String>();
            for(int index = 0; index < indBioRepository.findAll().size(); index++){
                if(indBioRepository.findAll().get(index).getEmail().equals(email)){
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
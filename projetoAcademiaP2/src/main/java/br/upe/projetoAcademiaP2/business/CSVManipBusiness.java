package br.upe.projetoAcademiaP2.business;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class CSVManipBusiness {
    public ArrayList<String> leitor(String caminho) {
        ArrayList<String> resposta = new ArrayList<>();

        try {
            BufferedReader leitor = new BufferedReader(new FileReader(caminho));
            String linha;
            boolean primeiraLinha = true;
            while ((linha = leitor.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue;
                }
                String[] vector = linha.split("\n");
                String instancia = vector[0];
                resposta.add(instancia);
            }
            leitor.close();
        } catch (IOException IOE) {
            IOE.printStackTrace();
        }
        return resposta;
    }
    public void escritor(ArrayList<String> nomeDosCampos, ArrayList<String> input, String nomeDoArquivo, String caminhoDoArquivo){
        String caminhoAbsoluto = caminhoDoArquivo + "/" + nomeDoArquivo;

        try {
            File directory = new File(caminhoAbsoluto);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            FileWriter writer = new FileWriter(caminhoAbsoluto, StandardCharsets.UTF_8,true);

            for (int index = 0; index < input.size(); index++) {
                if(index == 0){
                    for(int ind = 0; ind < nomeDosCampos.size();ind++){
                        writer.write(nomeDosCampos.get(ind));
                        if(ind != nomeDosCampos.size()-1){
                            writer.write(",");
                        }
                        writer.write("\n");
                    }
                }else {
                    writer.write(input.get(index) + "\n");
                }
            }
            writer.flush();
            writer.close();
        }catch (IOException IOE){
            IOE.printStackTrace();
        }
    }
}
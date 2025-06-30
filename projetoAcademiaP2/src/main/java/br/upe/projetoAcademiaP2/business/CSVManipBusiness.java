package br.upe.projetoAcademiaP2.business;
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
//    public void escritor(ArrayList<String> input, String nomeDoArquivo){
//        String directoryPath = "src/main/java/org/org.example/Analise";
//        String filePath = directoryPath + "/" + nomeDoArquivo;
//
//        try {
//            File directory = new File(directoryPath);
//            if (!directory.exists()) {
//                directory.mkdirs();
//            }
//            FileWriter writer = new FileWriter(filePath, StandardCharsets.UTF_8,true);
//
//            for (String s : input) {
//                writer.write(s + "\n");
//            }
//            writer.flush();
//            writer.close();
//        }catch (IOException IOE){
//            IOE.printStackTrace();
//        }
//    }
}
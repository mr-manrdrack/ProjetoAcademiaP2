package br.upe.projetoAcademiaP2.ui;

import br.upe.projetoAcademiaP2.business.CSVManipBusiness;
import br.upe.projetoAcademiaP2.data.beans.IndicadorBiomedico;
import br.upe.projetoAcademiaP2.data.beans.Usuario;

import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.*;

public class Relatorios {
    private final Scanner sc = new Scanner(System.in);
    private final String caminhoCSV = "data/indicadores.csv";
    private final CSVManipBusiness csvManip = new CSVManipBusiness();

    public void exibirMenuRelatorios(Usuario usuario) {
        boolean sair = false;

        while (!sair) {
            System.out.println("=".repeat(20));
            System.out.println("RELATÓRIOS");
            System.out.println("=".repeat(20));
            System.out.println("1 - Relatório Geral");
            System.out.println("2 - Relatório Comparativo");
            System.out.println("3 - Sair");
            System.out.print("Escolha uma opção: ");

            try {
                int opcao = sc.nextInt();
                sc.nextLine();

                switch (opcao) {
                    case 1:
                        relatorioGeral(usuario);
                        break;
                    case 2:
                        relatorioComparativo(usuario);
                        break;
                    case 3:
                        sair = true;
                        break;
                    default:
                        System.out.println("Opção inválida! Tente novamente.");
                }
            } catch (Exception e) {
                System.out.println("Erro: Entrada inválida!");
                sc.nextLine();
            }
        }
    }

    private void relatorioGeral(Usuario usuario) {
        List<IndicadorBiomedico> lista = carregarIndicadores(usuario.getEmail());

        if (lista.isEmpty()) {
            System.out.println("Nenhum indicador encontrado para este usuário.");
            return;
        }

        System.out.println("\nRELATÓRIO GERAL:");
        List<List<String>> dadosParaCSV = new ArrayList<>();

        for (IndicadorBiomedico ind : lista) {
            imprimirIndicador(ind);
            dadosParaCSV.add(Arrays.asList(
                    ind.getEmail(),
                    String.format(Locale.US, "%.2f", ind.getPeso()),
                    String.format(Locale.US, "%.2f", ind.getAltura()),
                    String.format(Locale.US, "%.2f", ind.getPercentualGordura()),
                    String.format(Locale.US, "%.2f", ind.getPercentualMassaMagra()),
                    String.format(Locale.US, "%.2f", ind.getImc()),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(ind.getDataRegistro())
            ));
        }

        salvarCSV("relatorio_geral_" + usuario.getEmail().replace("@", "_") + ".csv",
                Arrays.asList("Email", "Peso", "Altura", "Gordura", "Massa Magra", "IMC", "Data Registro"),
                dadosParaCSV);
    }

    private void relatorioComparativo(Usuario usuario) {
        List<IndicadorBiomedico> lista = carregarIndicadores(usuario.getEmail());

        if (lista.isEmpty()) {
            System.out.println("Nenhum indicador encontrado para este usuário.");
        } else if (lista.size() == 1) {
            System.out.println("\nApenas um registro encontrado. Exibindo:");
            imprimirIndicador(lista.get(0));

            List<List<String>> dados = List.of(Arrays.asList(
                    "Único",
                    String.format(Locale.US, "%.2f", lista.get(0).getPeso()),
                    String.format(Locale.US, "%.2f", lista.get(0).getAltura()),
                    String.format(Locale.US, "%.2f", lista.get(0).getPercentualGordura()),
                    String.format(Locale.US, "%.2f", lista.get(0).getPercentualMassaMagra()),
                    String.format(Locale.US, "%.2f", lista.get(0).getImc()),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(lista.get(0).getDataRegistro())
            ));

            salvarCSV("relatorio_comparativo_" + usuario.getEmail().replace("@", "_") + ".csv",
                    Arrays.asList("Tipo", "Peso", "Altura", "Gordura", "Massa Magra", "IMC", "Data Registro"),
                    dados);

        } else {
            IndicadorBiomedico primeiro = lista.get(0);
            IndicadorBiomedico ultimo = lista.get(lista.size() - 1);

            System.out.println("\nRELATÓRIO COMPARATIVO:");
            System.out.println("- Primeiro registro:");
            imprimirIndicador(primeiro);
            System.out.println("- Último registro:");
            imprimirIndicador(ultimo);

            List<List<String>> dados = List.of(
                    Arrays.asList("Primeiro",
                            String.format(Locale.US, "%.2f", primeiro.getPeso()),
                            String.format(Locale.US, "%.2f", primeiro.getAltura()),
                            String.format(Locale.US, "%.2f", primeiro.getPercentualGordura()),
                            String.format(Locale.US, "%.2f", primeiro.getPercentualMassaMagra()),
                            String.format(Locale.US, "%.2f", primeiro.getImc()),
                            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(primeiro.getDataRegistro())),
                    Arrays.asList("Último",
                            String.format(Locale.US, "%.2f", ultimo.getPeso()),
                            String.format(Locale.US, "%.2f", ultimo.getAltura()),
                            String.format(Locale.US, "%.2f", ultimo.getPercentualGordura()),
                            String.format(Locale.US, "%.2f", ultimo.getPercentualMassaMagra()),
                            String.format(Locale.US, "%.2f", ultimo.getImc()),
                            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(ultimo.getDataRegistro()))
            );

            salvarCSV("relatorio_comparativo_" + usuario.getEmail().replace("@", "_") + ".csv",
                    Arrays.asList("Tipo", "Peso", "Altura", "Gordura", "Massa Magra", "IMC", "Data Registro"),
                    dados);
        }
    }

    private List<IndicadorBiomedico> carregarIndicadores(String emailUsuario) {
        List<IndicadorBiomedico> resultado = new ArrayList<>();

        try {
            ArrayList<String> linhas = csvManip.leitor(caminhoCSV);
            for (String linha : linhas) {
                String[] dados = linha.split(",");
                if (dados.length >= 7 && dados[0].equals(emailUsuario)) {
                    String email = dados[0];
                    double peso = Double.parseDouble(dados[1]);
                    double altura = Double.parseDouble(dados[2]);
                    double gordura = Double.parseDouble(dados[3]);
                    double massa = Double.parseDouble(dados[4]);
                    double imc = Double.parseDouble(dados[5]);
                    Date data = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dados[6]);

                    resultado.add(new IndicadorBiomedico(email, peso, altura, gordura, massa, imc, data));
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar dados do CSV: " + e.getMessage());
        }

        return resultado;
    }

    private void imprimirIndicador(IndicadorBiomedico ind) {
        System.out.println("-".repeat(50));
        System.out.println("Data: " + ind.getDataRegistro());
        System.out.println("Peso: " + ind.getPeso() + " kg");
        System.out.println("Altura: " + ind.getAltura() + " m");
        System.out.println("IMC: " + String.format("%.2f", ind.getImc()));
        System.out.println("Gordura: " + ind.getPercentualGordura() + "%");
        System.out.println("Massa Magra: " + ind.getPercentualMassaMagra() + "%");
    }

    private void salvarCSV(String nomeArquivo, List<String> cabecalho, List<List<String>> linhas) {
        try (FileWriter writer = new FileWriter("data/" + nomeArquivo)) {
            writer.append(String.join(",", cabecalho)).append("\n");
            for (List<String> linha : linhas) {
                writer.append(String.join(",", linha)).append("\n");
            }
            System.out.println("✅ Arquivo gerado: data/" + nomeArquivo);
        } catch (Exception e) {
            System.out.println("Erro ao salvar CSV: " + e.getMessage());
        }
    }
}
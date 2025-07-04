package br.upe.projetoAcademiaP2.ui;
import br.upe.projetoAcademiaP2.business.*;
import br.upe.projetoAcademiaP2.data.beans.*;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class InterfaceBiomedico {

    private IndicadorBiomedicoBusiness indicadorBiomedicoBusiness;
    private Scanner sc = new Scanner(System.in);
    private Usuario usuarioLogado;

    public InterfaceBiomedico() {
        indicadorBiomedicoBusiness = new IndicadorBiomedicoBusiness();
    }

    public void exibirMenuIndicadores(Usuario aluno) {
        usuarioLogado = new Comum(aluno.getNome(), aluno.getTelefone(), aluno.getEmail(), aluno.getSenha(), aluno.getPesoAtual(),aluno.getAlturaAtual(),aluno.getPercGorduraAtual());
        boolean voltar = false;
        while (!voltar) {
            System.out.println("\n=== INDICADORES BIOMÉDICOS ===");
            System.out.println("1 - Cadastrar indicadores");
            System.out.println("2 - Listar indicadores");
            System.out.println("3 - Importar indicadores (CSV)");
            System.out.println("4 - Voltar");
            System.out.print("Escolha uma opção: ");

            try {
                int opcao = sc.nextInt();
                sc.nextLine();

                switch (opcao) {
                    case 1:
                        cadastrarIndicadores();
                        break;
                    case 2:
                        listarIndicadores();
                        break;
                    case 3:
                        importarIndicadoresCSV();
                        break;
                    case 4:
                        voltar = true;
                        break;
                    default:
                        System.out.println("Opção inválida! Tente novamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Erro: Entrada inválida. Por favor, digite um número.");
                sc.nextLine();
            }
        }
    }

    private void cadastrarIndicadores() {
        System.out.println("\n=== CADASTRAR INDICADORES BIOMÉDICOS ===");

        try {
            System.out.print("Peso (kg): ");
            double peso = sc.nextDouble();
            sc.nextLine();
            System.out.print("Altura (m): ");
            double altura = sc.nextDouble();
            sc.nextLine();
            System.out.print("Percentual de gordura (%): ");
            double percGordura = sc.nextDouble();
            sc.nextLine();
            System.out.print("Percentual de massa magra (%): ");
            double percMassaMagra = sc.nextDouble();
            sc.nextLine();

            String id = usuarioLogado.getEmail();
            double imc = peso / (altura * altura);
            IndicadorBiomedico indicador = new IndicadorBiomedico(id, peso, altura, percGordura, percMassaMagra, imc, new Date());

            indicadorBiomedicoBusiness.cadastrarIndicador(usuarioLogado, indicador);

            System.out.println("✅ Indicador cadastrado com sucesso!");

        } catch (InputMismatchException e) {
            System.out.println("Erro: Digite valores numéricos válidos.");
            sc.nextLine();
        }
    }

    private void listarIndicadores() {
        System.out.println("\n=== SEUS INDICADORES BIOMÉDICOS ===");

        List<IndicadorBiomedico> indicadores = indicadorBiomedicoBusiness.listarIndicadores(usuarioLogado);

        if (indicadores.isEmpty()) {
            System.out.println("Nenhum indicador biomédico cadastrado.");
            return;
        }

        System.out.println("Total de registros: " + indicadores.size());
        System.out.println("-".repeat(60));

        for (int i = 0; i < indicadores.size(); i++) {
            IndicadorBiomedico ind = indicadores.get(i);
            System.out.println("Registro " + (i + 1) + " (Usuário: " + ind.getEmail() + ")");
            System.out.println("Peso: " + ind.getPeso() + " kg");
            System.out.println("Altura: " + ind.getAltura() + " m");
            System.out.println("IMC: " + String.format("%.2f", ind.getImc()));
            System.out.println("Gordura: " + ind.getPercentualGordura() + "%");
            System.out.println("Massa Magra: " + ind.getPercentualMassaMagra() + "%");
            System.out.println("Data: " + ind.getDataRegistro());
            System.out.println("-".repeat(60));
        }
    }

    private void importarIndicadoresCSV() {
        System.out.println("\n=== IMPORTAR INDICADORES BIOMÉDICOS (CSV) ===");
        System.out.println("Formato esperado do CSV:");
        System.out.println("peso,altura,percentualGordura,percentualMassaMagra");
        System.out.println("Exemplo: 70.5,1.75,15.2,84.8");
        System.out.println();

        System.out.print("Digite o caminho completo do arquivo CSV: ");
        String caminhoArquivo = sc.nextLine();

        boolean importado = indicadorBiomedicoBusiness.importarIndicadoresDeCSV(usuarioLogado, caminhoArquivo);

        if (importado) {
            System.out.println("Indicadores importados com sucesso!");
        } else {
            System.out.println("Erro ao importar indicadores. Verifique o formato do arquivo.");
        }
    }

    public void relatorioEvolucao() {
    }
}
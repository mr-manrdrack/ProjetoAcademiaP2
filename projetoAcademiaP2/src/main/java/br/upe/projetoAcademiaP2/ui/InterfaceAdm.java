package br.upe.projetoAcademiaP2.ui;

public class InterfaceAdm extends InterfaceAluno{

    InterfaceAdm(){
        super();
    }

    @Override
    public void exibirMenu(){
        boolean sair = false;

        while (!sair){
            System.out.println("---- MENU ADMINISTRADOR ----");
            System.out.println("1 - Gerenciar Alunos\n2 - Sair");
            System.out.println("Escolha uma opção: ");
            int opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao){
                case 1:
                    gerenciarAlunos();
                    break;
                case 2:
                    sair = true;
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }

    public void gerenciarAlunos(){
        System.out.println("1 - Cadastrar aluno\n2 - Listar alunos\n3 - Atualizar dados do aluno\n4 - Excluir alunos\n5 - Sair");
        System.out.println("Escolha uma opção: ");
        int opcao = sc.nextInt();
        sc.nextLine();

        switch (opcao){
            case 1:
                //cadastrar aluno
                break;
            case 2:
                //listar alunos
                break;
            case 3:
                //atualizar dados do aluno
                break;
            case 4:
                //excluir aluno
                break;
            case 5:
                break;
            default:
                System.out.println("Opção inválida! Tente novamente.");
        }
    }
}
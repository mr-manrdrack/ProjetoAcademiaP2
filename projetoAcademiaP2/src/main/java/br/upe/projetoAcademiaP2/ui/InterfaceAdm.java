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

            switch (opcao){
                case 1:
                    System.out.println("1 - Cadastrar aluno\n2 - Listar alunos\n3 - Atualizar dados do aluno\n4 - Excluir alunos\n5 - Sair");
                case 2:
                    sair = true;
            }
        }
    }
}
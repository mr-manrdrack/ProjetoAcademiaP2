package br.upe.projetoAcademiaP2.ui;

import java.util.Scanner;
import br.upe.projetoAcademiaP2.business.UsuarioBusiness;
import br.upe.projetoAcademiaP2.data.beans.Usuario;
import br.upe.projetoAcademiaP2.data.repository.UsuarioCsvRepository;

public class App {

    private static Scanner sc = new Scanner(System.in);
    private static UsuarioBusiness usuarioBusiness = new UsuarioBusiness();
    private static UsuarioCsvRepository usuarioCsvRepository = new UsuarioCsvRepository();

    public static void main (String[] args){

        while (true){
            System.out.println("---- SISTEMA ACADEMIA ----");
            System.out.println("1 - Login\n2 - Sair");
            System.out.println("Escolha uma opção: ");

            int opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao){
                case 1:
                    realizarLogin();
                    break;
                case 2:
                    System.out.println("Saindo do sistema.");
                    return;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }

    private static void realizarLogin() {
        System.out.print("E-mail: ");
        String email = sc.nextLine();
        System.out.print("Senha: ");
        String senha = sc.nextLine();

        String tipoUsuario = usuarioBusiness.autenticar(email, senha);

        if (tipoUsuario != null) {
            Usuario usuarioLogado = usuarioCsvRepository.findByEmail(email);
            
            if (usuarioLogado == null) {
                System.out.println("Erro crítico: usuário autenticado mas não encontrado. Contate o suporte.");
                return;
            }

            System.out.println("\nLogin realizado com sucesso!");

            switch (tipoUsuario) {
                case "ADM":
                    InterfaceAdm interfaceAdm = new InterfaceAdm(usuarioLogado);
                    interfaceAdm.exibirMenu();
                    break;
                case "COMUM":
                    // ALTERADO: Passamos o objeto do aluno para o construtor
                    InterfaceAluno interfaceAluno = new InterfaceAluno(usuarioLogado);
                    interfaceAluno.exibirMenu();
                    break;
            }
        } else {
            System.out.println("E-mail ou senha inválidos! Tente novamente.");
        }
    }
}
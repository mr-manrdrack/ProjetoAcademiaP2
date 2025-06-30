package br.upe.projetoAcademiaP2.ui;


import br.upe.projetoAcademiaP2.business.UsuarioBusiness;
import br.upe.projetoAcademiaP2.data.beans.Usuario;
import br.upe.projetoAcademiaP2.data.repository.UsuarioCsvRepository;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InterfacePrincipal {

    private final Scanner sc;
    private static UsuarioBusiness usuarioBusiness = new UsuarioBusiness();
    private static UsuarioCsvRepository usuarioCsvRepository = new UsuarioCsvRepository();

    public InterfacePrincipal(Scanner scGlobal) {
        this.sc = scGlobal;
    }

    public void exibirMenuPrincipal() {
        boolean sair = false;
        while (!sair){
            System.out.println("=".repeat(20));
            System.out.println("BEM-VINDO");
            System.out.println("=".repeat(20));
            System.out.println("1 - Entrar");
            System.out.println("2 - Sair");
            System.out.print("Escolha uma opção: ");

            try{
                int opcao = sc.nextInt();
                sc.nextLine();

                switch (opcao){
                    case 1:
                        realizarLogin();
                        break;
                    case 2:
                        System.out.println("Saindo...");
                        sair = true;
                        break;
                    default:
                        System.out.println("Opção inválida! Tente novamente");
                }
            }catch (InputMismatchException e){
                System.out.println("Erro: Entrada inválida!");
                sc.nextLine();
            }
        }
    }

    private void realizarLogin() {

        System.out.print("Email: ");
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
                    interfaceAdm.exibirMenuAdm();
                    break;
                case "COMUM":
                    InterfaceAluno interfaceAluno = new InterfaceAluno(usuarioLogado);
                    interfaceAluno.exibirMenuAlunos();
                    break;
            }
        } else {
            System.out.println("E-mail ou senha inválidos! Tente novamente.");
        }
    }
}
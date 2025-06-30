package br.upe.projetoAcademiaP2;

import br.upe.projetoAcademiaP2.ui.InterfacePrincipal;
import java.util.Scanner;

public class Main {
    public static void main(String[]args){
        Scanner scGlobal = new Scanner(System.in);

        InterfacePrincipal interfacePrincipal = new InterfacePrincipal(scGlobal);
        interfacePrincipal.exibirMenuPrincipal();
    }
}
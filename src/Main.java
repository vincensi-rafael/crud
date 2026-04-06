import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.PrintWriter;

public class Main {
    static ArrayList<String> usuarios = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        carregarUsuarios();
        int opcao;
        do {
            System.out.println("\n1 - Adicionar usuário");
            System.out.println("2 - Listar usuários");
            System.out.println("3 - Atualizar usuário");
            System.out.println("4 - Remover usuário");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch(opcao) {
                case 1: adicionar(); break;
                case 2: listar(); break;
                case 3: atualizar(); break;
                case 4: remover(); break;
            }
        } while(opcao != 0);

        scanner.close();
    }

    static void adicionar() {
        System.out.print("Nome do usuário: ");
        String nome = scanner.nextLine();
        usuarios.add(nome);
        salvarArquivo(nome);
        System.out.println("Usuário adicionado!");
    }

    static void salvarArquivo(String nome) {
        try {
            File file = new File("data/usuarios.txt");
            file.getParentFile().mkdirs();
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw);
            out.println(nome);
            out.close();
        } catch (IOException e) {
            System.out.println("Erro ao salvar no arquivo: " + e.getMessage());
        }
    }

    static void listar() {
        System.out.println("\nLista de usuários:");
        for(int i = 0; i < usuarios.size(); i++) {
            System.out.println(i + " - " + usuarios.get(i));
        }
    }

    static void atualizar() {
        listar();
        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário cadastrado para atualizar!");
            return;
        }
        System.out.print("Escolha o ID do usuário para atualizar: ");
        String entrada = scanner.nextLine();
        int id;
        try {
            id = Integer.parseInt(entrada);
        } catch (NumberFormatException e) {
            System.out.println("ID inválido! Digite apenas números.");
            return;
        }

        if(id >= 0 && id < usuarios.size()) {
            System.out.print("Novo nome: ");
            String novoNome = scanner.nextLine();
            usuarios.set(id, novoNome);
            System.out.println("Usuário atualizado!");
            reescreverArquivo();
        } else {
            System.out.println("ID inválido!");
        }
    }

    static void remover() {
        listar();
        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário cadastrado para remover!");
            return;
        }
        System.out.print("Escolha o ID do usuário para remover: ");
        String entrada = scanner.nextLine();
        int id;
        try {
            id = Integer.parseInt(entrada);
        } catch (NumberFormatException e) {
            System.out.println("ID inválido! Digite apenas números.");
            return;
        }

        if(id >= 0 && id < usuarios.size()) {
            String removido = usuarios.remove(id);
            System.out.println("Usuário '" + removido + "' removido!");
            reescreverArquivo();
        } else {
            System.out.println("ID inválido!");
        }
    }

    static void carregarUsuarios() {
        File file = new File("data/usuarios.txt");
        if (!file.exists()) return;

        try (Scanner fileScanner = new Scanner(file)) {
            while(fileScanner.hasNextLine()) {
                String linha = fileScanner.nextLine();
                if (!linha.isEmpty()) {
                    usuarios.add(linha);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo: " + e.getMessage());
        }
    }

    static void reescreverArquivo() {
        try {
            File file = new File("data/usuarios.txt");
            file.getParentFile().mkdirs();
            FileWriter fw = new FileWriter(file, false);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw);
            for(String u : usuarios) {
                out.println(u);
            }
            out.close();
        } catch (IOException e) {
            System.out.println("Erro ao salvar no arquivo: " + e.getMessage());
        }
    }
}
// view/MenuView.java

package view;

import dtos.CourseCatalogDTO;
import dtos.EnrollmentDTO;
import model.CourseStatus;

import java.util.Collection;
import java.util.Scanner;

public class MenuView {

    private final Scanner scanner = new Scanner(System.in);

    // === TELAS GERAIS ===
    public void mostrarBoasVindas() {
        System.out.println("=== Bem-vindo à AcademiaDev! ===\n");
    }

    public String lerEmail() {
        System.out.print("Digite seu email (ou 'X' para sair): ");
        return scanner.nextLine().trim();
    }

    public void mostrarMensagemLoginSucesso(String nome) {
        System.out.println("✅ Login realizado como: " + nome + "\n");
    }

    public void mostrarErro(String mensagem) {
        System.out.println("" + mensagem + "\n");
    }

    public void mostrarMensagemSistemaEncerrado() {
        System.out.println("Encerrando o sistema. Até logo!");
    }

    // === MENU ADMIN ==
    public int mostrarMenuAdmin() {
        System.out.println("\n--- Menu Admin ---");
        System.out.println("1. Listar todos os cursos");
        System.out.println("2. Ativar/Inativar curso");
        System.out.println("3. Atender próximo ticket");
        System.out.println("4. Gerar relatórios");
        System.out.println("5. Exportar dados para CSV");
        System.out.println("6. Sair da conta");
        System.out.println("7. Sair do sistema");
        System.out.print("Escolha: ");
        return lerOpcao();
    }

    public String lerTituloCurso() {
        System.out.print("Digite o título do curso: ");
        return scanner.nextLine().trim();
    }

    public boolean confirmarAlteracaoStatus(String curso, CourseStatus atual, CourseStatus novo) {
        System.out.println("Curso: " + curso);
        System.out.println("Status atual: " + atual);
        System.out.println("Deseja mudar para: " + novo + "? (S/N)");
        String resposta = scanner.nextLine().trim();
        return resposta.equalsIgnoreCase("S");
    }

    public void mostrarStatusAtualizado(String curso, CourseStatus novoStatus) {
        System.out.println("\nStatus atualizado com sucesso!");
        System.out.println("Curso: " + curso);
        System.out.println("Novo status: " + novoStatus);
        System.out.println();
    }

    // === MENU ALUNO ===
    public int mostrarMenuAluno() {
        System.out.println("\n--- Menu Aluno ---");
        System.out.println("1. Ver catálogo de cursos ativos");
        System.out.println("2. Ver cursos por dificuldade");
        System.out.println("3. Matricular-se em curso");
        System.out.println("4. Ver minhas matrículas");
        System.out.println("5. Atualizar progresso");
        System.out.println("6. Cancelar matrícula");
        System.out.println("7. Abrir ticket de suporte");
        System.out.println("8. Sair da conta");
        System.out.println("9. Sair do sistema");
        System.out.print("Escolha: ");
        return lerOpcao();
    }

    public void mostrarCursos(Collection<CourseCatalogDTO> cursos) {
        if (cursos.isEmpty()) {
            System.out.println("Nenhum curso encontrado.\n");
        } else {
            System.out.println("\nCursos disponíveis:");
            cursos.forEach(System.out::println);
        }
        System.out.println();
    }

    public void mostrarEnrollments(Collection<EnrollmentDTO> enrollments) {
        if (enrollments.isEmpty()) {
            System.out.println("Nenhuma matrícula encontrada.\n");
        } else {
            System.out.println("\nSuas matrículas:");
            enrollments.forEach(System.out::println);
        }
        System.out.println();
    }

    public int lerProgresso() {
        System.out.print("Digite o novo progresso (0 a 100%): ");
        String input = scanner.nextLine().trim();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Progresso deve ser um número válido.");
        }
    }

    public void mostrarProgressoAtualizado(String curso, int progresso) {
        System.out.println("\nProgresso atualizado com sucesso!");
        System.out.println("Curso: " + curso);
        System.out.println("Progresso: " + progresso + "%");
        if (progresso == 100) {
            System.out.println("Parabéns, você concluiu o curso!");
        } else {
            System.out.println("Continue assim!");
        }
        System.out.println();
    }

    public void mostrarMatriculaRealizada(String curso) {
        System.out.println("\nMatrícula realizada com sucesso!");
        System.out.println("Curso: " + curso);
        System.out.println("Progresso: 0%\n");
    }

    public void mostrarMatriculaCancelada(String curso) {
        System.out.println("\nMatrícula no curso '" + curso + "' cancelada com sucesso!\n");
    }

    public String lerTicketTitle() {
        System.out.print("Título do ticket: ");
        return scanner.nextLine().trim();
    }

    public String lerTicketMessage() {
        System.out.print("Mensagem: ");
        return scanner.nextLine().trim();
    }

    public void mostrarTicketAberto(String title, String author, String message) {
        System.out.println("Ticket aberto com sucesso!");
        System.out.println("===========DADOS DO TICKET===========");
        System.out.println("Autor: " + author);        
        System.out.println("Título: " + title);
        System.out.println("Mensagem: " + message);
        System.out.println("====================================");
    }

    // === AUXILIAR ===
    private int lerOpcao() {
        String input = scanner.nextLine().trim();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
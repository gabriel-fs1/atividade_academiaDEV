// view/MenuView.java

package view;

import dtos.CourseCatalogDTO;
import dtos.EnrollmentDTO;
import dtos.SupportTicketDTO;
import dtos.UserSummaryDTO;
import model.CourseStatus;
import model.DifficultyLevel;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

public class MenuView {

    private final Scanner scanner = new Scanner(System.in);

    // === TELAS GERAIS ===
    public void mostrarBoasVindas() {
        System.out.println("=== Bem-vindo à AcademiaDev! ===\n");
    }

    public DifficultyLevel selecionarDificuldade() {
        System.out.println("\n--- Selecione a dificuldade ---");
        System.out.println("1. Facil");
        System.out.println("2. Medio");
        System.out.println("3. Dificil");
        System.out.print("Escolha: ");
        String escolha = scanner.nextLine().trim();

        return switch (escolha) {
            case "1" -> DifficultyLevel.BEGINNER;
            case "2" -> DifficultyLevel.INTERMEDIATE;
            case "3" -> DifficultyLevel.ADVANCED;
            default -> {
                System.out.println("Opção inválida. Assumindo 'Iniciante'.");
                yield DifficultyLevel.BEGINNER;
            }
        };
    }

    public String lerEmail() {
        System.out.print("Digite seu email (ou 'X' para sair): ");
        return scanner.nextLine().trim();
    }

    public void mostrarMensagemLoginSucesso(String nome) {
        System.out.println("Login realizado como: " + nome + "\n");
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
        System.out.println("6. Alterar plano de alunos");
        System.out.println("7. Sair da conta");
        System.out.println("8. Sair do sistema");
        System.out.print("Escolha: ");
        return lerOpcao();
    }

    public boolean confirmarAlteracaoPlano(String email, String atual, String novo) {
        System.out.println("email: " + email);
        System.out.println("Plano atual: " + atual);
        System.out.println("Deseja mudar para: " + novo + "? (S/N)");
        String resposta = scanner.nextLine().trim();
        return resposta.equalsIgnoreCase("S");
    }

    public void mostrarPlanoAtualizado(String email, String novoStatus) {
        System.out.println("\nStatus atualizado com sucesso!");
        System.out.println("Curso: " + email);
        System.out.println("Novo status: " + novoStatus);
        System.out.println();
    }

    public String lerEmailAluno() {
        System.out.print("Digite o email do aluno: ");
        return scanner.nextLine().trim();
    }

    public void mostrarTicketAtendido(Optional<SupportTicketDTO> optional) {
        if (optional.isPresent()) {
            SupportTicketDTO ticket = optional.get();
            mostrarTicket(ticket.getEmailAuthor(),
                    ticket.getTitle(),
                    ticket.getDescription());
                    
        } else {
            System.out.println("Nenhum ticket para atender.");
        }
    }

    public void mostrarAlunoComMaiorMatricula(Optional<UserSummaryDTO> aluno) {
        if (aluno.isPresent()) {
            System.out.println("\nAluno com mais matrículas:");
            System.out.println(aluno.get());
        } else {
            System.out.println("\nNenhum aluno encontrado.\n");
        }

    }

    public void mostrarMediaProgresso(double media) {
        if (media == -1) {
            System.out.println("\nNenhuma matrícula encontrada.\n");
        } else {
            System.out.println("\nMédia de progresso: " + media + "%\n");
        }
    }

    public void mostrarAlunoAgrupado(Map<String, List<UserSummaryDTO>> alunos) {
        if (alunos.isEmpty()) {
            System.out.println("Nenhum aluno encontrado.\n");
        } else {
            System.out.println("\nAlunos:");
            alunos.forEach((plano, alunosDoPlano) -> System.out.println(plano + ": " + alunosDoPlano));
        }
        System.out.println();
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
        System.out.println("2. Matricular-se em curso");
        System.out.println("3. Ver minhas matrículas");
        System.out.println("4. Atualizar progresso");
        System.out.println("5. Cancelar matrícula");
        System.out.println("6. Abrir ticket de suporte");
        System.out.println("7. Sair da conta");
        System.out.println("8. Sair do sistema");
        System.out.print("Escolha: ");
        return lerOpcao();
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

    public void mostrarInstrutores(Set<String> instructor) {
        if (instructor.isEmpty()) {
            System.out.println("Nenhum instrutor encontrado.\n");
        } else {
            System.out.println("\nInstrutores:");
            instructor.forEach(System.out::println);
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

    public void mostrarTicket(String author, String title, String message) {
        System.out.println("\nTicket aberto com sucesso!");
        System.out.println("===========DADOS DO TICKET===========");
        System.out.println("Autor: " + author);
        System.out.println("Título: " + title);
        System.out.println("Mensagem: " + message);
        System.out.println("=====================================");
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
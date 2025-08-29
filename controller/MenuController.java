// controller/MenuController.java

package controller;

import dtos.CourseCatalogDTO;
import dtos.EnrollmentDTO;
import model.CourseStatus;
import model.DifficultyLevel;
import model.User;
import service.CourseService;
import service.EnrollmentService;
import service.SupportTicketService;
import service.UserService;
import view.MenuView;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class MenuController {

    private final UserService userService;
    private final CourseService courseService;
    private final EnrollmentService enrollmentService;
    private final SupportTicketService supportTicketService;
    private final MenuView view;

    private User currentUser;
    private Scanner scanner = new Scanner(System.in);

    public MenuController(UserService userService,
                          CourseService courseService,
                          EnrollmentService enrollmentService,
                          SupportTicketService supportTicketService,
                          MenuView view) {
        this.userService = userService;
        this.courseService = courseService;
        this.enrollmentService = enrollmentService;
        this.supportTicketService = supportTicketService;
        this.view = view;
    }

    public void start() {
        view.mostrarBoasVindas();

        while (true) {
            currentUser = null;
            boolean shouldExit = login();
            if (shouldExit) {
                view.mostrarMensagemSistemaEncerrado();
                break;
            }

            if (currentUser instanceof model.Admin) {
                showAdminMenu();
            } else if (currentUser instanceof model.Student) {
                showStudentMenu();
            }
        }
    }

    private boolean login() {
        while (currentUser == null) {
            String email = view.lerEmail();
            if (email.equalsIgnoreCase("X")) {
                return true;
            }
            if (email.isEmpty()) {
                view.mostrarErro("Email não pode ser vazio");
                continue;
            }

            try {
                var dto = userService.login(email);
                view.mostrarMensagemLoginSucesso(dto.getName());

                currentUser = userService.findFullUserByEmail(email)
                        .orElse(null);
                if (currentUser == null) {
                    view.mostrarErro("Usuário não encontrado");
                }

            } catch (RuntimeException e) {
                view.mostrarErro(e.getMessage());
            }
        }
        return false;
    }

    private void showAdminMenu() {
        while (true) {
            int opcao = view.mostrarMenuAdmin();

            switch (opcao) {
                case 1:
                    view.mostrarCursos(courseService.findAll());
                    break;
                case 2:
                    ativarOuInativarCurso();
                    break;
                case 3:
                    
                    break;
                case 4:
                    
                case 5:
                    
                case 6:
                    return; // sair da conta
                case 7:
                    System.exit(0);
                default:
                    view.mostrarErro("Opção inválida");
            }
        }
    }

    private void ativarOuInativarCurso() {
        String title = view.lerTituloCurso();
        if (title.isEmpty()) {
            view.mostrarErro("Título não pode ser vazio");
            return;
        }

        Optional<CourseCatalogDTO> dtoOpt = courseService.findByTitle(title);
        if (dtoOpt.isEmpty()) {
            view.mostrarErro("Curso não encontrado");
            return;
        }

        CourseCatalogDTO dto = dtoOpt.get();
        CourseStatus novoStatus = dto.getStatus() == CourseStatus.ACTIVE ? CourseStatus.INACTIVE : CourseStatus.ACTIVE;

        if (view.confirmarAlteracaoStatus(dto.getTitle(), dto.getStatus(), novoStatus)) {
            try {
                CourseCatalogDTO updated = courseService.updateCourseStatus(title, novoStatus);
                view.mostrarStatusAtualizado(updated.getTitle(), updated.getStatus());
            } catch (IllegalArgumentException e) {
                view.mostrarErro(e.getMessage());
            }
        } else {
            view.mostrarErro("Operação cancelada");
        }
    }

    private void showStudentMenu() {
        while (true) {
            int opcao = view.mostrarMenuAluno();

            switch (opcao) {
                case 1:
                    view.mostrarCursos(courseService.findActiveCourses());
                    break;
                case 2:
                    verCursosPorDificuldade();
                    break;
                case 3:
                    matricularEmCurso();
                    break;
                case 4:
                    verMinhasMatriculas();
                    break;
                case 5:
                    atualizarProgresso();
                    break;
                case 6:
                    cancelarMatricula();
                    break;
                case 7:
                    abrirTicket();
                    break;
                case 8:
                    return;
                case 9:
                    System.exit(0);
                default:
                    view.mostrarErro("Opção inválida");
            }
        }
    }

    private void verCursosPorDificuldade() {
        System.out.println("\n--- Dificuldade ---");
        System.out.println("1. Iniciante");
        System.out.println("2. Intermediário");
        System.out.println("3. Avançado");
        System.out.print("Escolha: ");
        int opcao = scanner.nextInt();

        Collection<CourseCatalogDTO> cursos;
        switch (opcao) {
            case 1:
                cursos = courseService.findByDifficultyLevel(DifficultyLevel.BEGINNER);
                break;
            case 2:
                cursos = courseService.findByDifficultyLevel(DifficultyLevel.INTERMEDIATE);
                break;
            case 3:
                cursos = courseService.findByDifficultyLevel(DifficultyLevel.ADVANCED);
                break;
            default:
                view.mostrarErro("Opção inválida");
                return;
        }
        view.mostrarCursos(cursos);
    }

    private void matricularEmCurso() {
        String title = view.lerTituloCurso();
        if (title.isEmpty()) {
            view.mostrarErro("Título não pode ser vazio");
            return;
        }

        try {
            EnrollmentDTO enrollment = enrollmentService.enrollStudentInCourse(currentUser.getEmail(), title);
            view.mostrarMatriculaRealizada(enrollment.getCourseTitle());
        } catch (IllegalArgumentException e) {
            view.mostrarErro("Erro ao matricular: " + e.getMessage());
        }
    }

    private void verMinhasMatriculas() {
        List<EnrollmentDTO> enrollments = enrollmentService.getEnrollmentsByStudent(currentUser.getEmail());
        view.mostrarEnrollments(enrollments);
    }

    private void atualizarProgresso() {
        String title = view.lerTituloCurso();
        if (title.isEmpty()) {
            view.mostrarErro("Título não pode ser vazio");
            return;
        }

        try {
            int progress = view.lerProgresso();
            EnrollmentDTO updated = enrollmentService.updateProgress(currentUser.getEmail(), title, progress);
            view.mostrarProgressoAtualizado(updated.getCourseTitle(), updated.getProgress());
        } catch (IllegalArgumentException e) {
            view.mostrarErro(e.getMessage());
        }
    }

    private void cancelarMatricula() {
        String title = view.lerTituloCurso();
        if (title.isEmpty()) {
            view.mostrarErro("Título não pode ser vazio");
            return;
        }

        try {
            enrollmentService.cancelEnrollment(currentUser.getEmail(), title);
            view.mostrarMatriculaCancelada(title);
        } catch (IllegalArgumentException e) {
            view.mostrarErro("Erro ao cancelar: " + e.getMessage());
        }
    }

    private void abrirTicket() {
        String title = view.lerTicketTitle();
        String message = view.lerTicketMessage();

        if (title.isEmpty() || message.isEmpty()) {
            view.mostrarErro("Título e mensagem não podem ser vazios");
            return;
        }

        supportTicketService.addTicket(title, message, currentUser);
        view.mostrarTicketAberto(title, currentUser.getName(), message);
    }
}
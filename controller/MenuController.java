// controller/MenuController.java

package controller;

import dtos.CourseCatalogDTO;
import dtos.CourseExportDTO;
import dtos.EnrollmentDTO;
import dtos.EnrollmentExportDTO;
import dtos.TicketExportDTO;
import dtos.UserExportDTO;
import dtos.UserSummaryDTO;
import model.BasicPlan;
import model.CourseStatus;
import model.DifficultyLevel;

import model.PremiumPlan;
import model.Student;
import model.SubscriptionPlan;
import model.User;
import service.CourseService;
import service.EnrollmentService;
import service.SupportTicketService;
import service.UserService;
import util.GenericCsvExporter;
import service.ReportService;
import view.MenuView;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

public class MenuController {

    private final UserService userService;
    private final CourseService courseService;
    private final EnrollmentService enrollmentService;
    private final SupportTicketService supportTicketService;
    private final MenuView view;
    private final ReportService reportService;
    private final GenericCsvExporter csvExporter = new GenericCsvExporter();

    private User currentUser;
    private Scanner scanner = new Scanner(System.in);

    public MenuController(UserService userService,
            CourseService courseService,
            EnrollmentService enrollmentService,
            SupportTicketService supportTicketService,
            MenuView view, ReportService reportService) {
        this.userService = userService;
        this.courseService = courseService;
        this.enrollmentService = enrollmentService;
        this.supportTicketService = supportTicketService;
        this.view = view;
        this.reportService = reportService;
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
                    atenderTicket();
                    break;
                case 4:
                    showReportsMenu();
                    break;
                case 5:
                    exportarDados();
                    break;
                case 6:
                    alterarPlano();
                    break;
                case 7:
                    return;
                case 8:
                    System.exit(0);
                default:
                    view.mostrarErro("Opção inválida");
            }
        }
    }

    private void exportarDados() {
        System.out.println("Escolha o tipo de dado para exportar:");
        System.out.println("1. Cursos");
        System.out.println("2. Usuarios");
        System.out.println("3. Matrículas");
        System.out.println("4. Tickets");
        System.out.println("5. Sair");
        System.out.print("Escolha: ");
        String opcao = scanner.nextLine().trim();

        switch (opcao) {
            case "1":
                exportarCursos();
                break;
            case "2":
                exportarUsuarios();
                break;
            case "3":
                exportarMatriculas();
                break;
            case "4":
                exportarTickets();
                break;
            case "5":
                return;
            default:
                System.out.println("Opção inválida.");
        }
    }

    private void exportarCursos() {

        Collection<CourseExportDTO> cursos = courseService.getAllCoursesForExport();

        List<String> campos = view.selecionarCamposParaCurso();
        if (campos.isEmpty()) {
            view.mostrarErro("Nenhum campo selecionado.");
            return;
        }

        String csv = csvExporter.exportToCsv(cursos, campos.toArray(new String[0]));

        System.out.println("\n=== DADOS EXPORTADOS ===\n");
        System.out.println(csv);
    }

    private void exportarMatriculas() {

        Collection<EnrollmentExportDTO> mat = enrollmentService.getAllEnrollmentsForExport();

        List<String> campos = view.selecionarCamposParaMatricula();
        if (campos.isEmpty()) {
            view.mostrarErro("Nenhum campo selecionado.");
            return;
        }

        String csv = csvExporter.exportToCsv(mat, campos.toArray(new String[0]));

        System.out.println("\n=== DADOS EXPORTADOS ===\n");
        System.out.println(csv);
    }

    private void exportarUsuarios() {

        Collection<UserExportDTO> u = userService.getAllUsersForExport();

        List<String> campos = view.selecionarCamposParaUsuarios();
        if (campos.isEmpty()) {
            view.mostrarErro("Nenhum campo selecionado.");
            return;
        }

        String csv = csvExporter.exportToCsv(u, campos.toArray(new String[0]));

        System.out.println("\n=== DADOS EXPORTADOS ===\n");
        System.out.println(csv);
    }

    private void exportarTickets() {

        Collection<TicketExportDTO> t = supportTicketService.getAllTicketsForExport();

        List<String> campos = view.selecionarCamposParaTickets();
        if (campos.isEmpty()) {
            view.mostrarErro("Nenhum campo selecionado.");
            return;
        }

        String csv = csvExporter.exportToCsv(t, campos.toArray(new String[0]));

        System.out.println("\n=== DADOS EXPORTADOS ===\n");
        System.out.println(csv);
    }

    private void alterarPlano() {
        String email = view.lerEmailAluno();
        if (email.isEmpty()) {
            view.mostrarErro("Email nao pode ser vazio");
            return;
        }

        Optional<UserSummaryDTO> dtoOpt = userService.findByEmail(email);
        if (dtoOpt.isEmpty()) {
            view.mostrarErro("Usuário nao encontrado");
            return;
        }
        UserSummaryDTO alunoDto = dtoOpt.get();

        if (!"STUDENT".equals(alunoDto.getRole())) {
            view.mostrarErro("Apenas alunos podem ter plano alterado.");
            return;
        }

        Student student = (Student) userService.findFullUserByEmail(email).orElse(null);
        if (student == null) {
            view.mostrarErro("Erro ao carregar dados do aluno.");
            return;
        }

        SubscriptionPlan novoPlano = student.getSubscriptionPlan() instanceof BasicPlan
                ? new PremiumPlan()
                : new BasicPlan();

        String planoAtual = student.getSubscriptionPlan().getPlanName();
        String novoPlanoNome = novoPlano.getPlanName();

        if (view.confirmarAlteracaoPlano(alunoDto.getName(), planoAtual, novoPlanoNome)) {
            try {
                userService.changeSubscriptionPlan(email, novoPlano, currentUser);
                view.mostrarPlanoAtualizado(email, novoPlanoNome);
            } catch (IllegalArgumentException e) {
                view.mostrarErro(e.getMessage());
            }
        } else {
            view.mostrarErro("Operação cancelada");
        }
    }

    private void atenderTicket() {
        view.mostrarTicketAtendido(supportTicketService.attendNextTicket(currentUser));
    }

    private void showReportsMenu() {
        while (true) {
            System.out.println("\n--- Relatórios ---");
            System.out.println("1. Cursos por dificuldade");
            System.out.println("2. Instrutores ativos");
            System.out.println("3. Alunos por plano");
            System.out.println("4. Média de progresso");
            System.out.println("5. Aluno com mais matrículas");
            System.out.println("6. Voltar");
            System.out.print("Escolha: ");

            String opcao = scanner.nextLine().trim();

            switch (opcao) {
                case "1":
                    DifficultyLevel level = view.selecionarDificuldade();
                    List<CourseCatalogDTO> cursos = reportService.getCoursesByDifficulty(level);
                    view.mostrarCursos(cursos);
                    break;
                case "2":
                    Set<String> instrutores = reportService.getActiveInstructors();
                    view.mostrarInstrutores(instrutores);
                    break;
                case "3":
                    Map<String, List<UserSummaryDTO>> alunosPorPlano = reportService.getStudentsByPlan();
                    view.mostrarAlunoAgrupado(alunosPorPlano);
                    break;
                case "4":
                    double media = reportService.getAverageProgress();
                    view.mostrarMediaProgresso(media);
                    break;
                case "5":
                    Optional<UserSummaryDTO> alunoMaisMatriculas = reportService.getStudentWithMostEnrollments();
                    view.mostrarAlunoComMaiorMatricula(alunoMaisMatriculas);
                    break;
                case "6":
                    return;
                default:
                    System.out.println("Opção inválida.");
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
                    matricularEmCurso();
                    break;
                case 3:
                    verMinhasMatriculas();
                    break;
                case 4:
                    atualizarProgresso();
                    break;
                case 5:
                    cancelarMatricula();
                    break;
                case 6:
                    abrirTicket();
                    break;
                case 7:
                    return;
                case 8:
                    System.exit(0);
                default:
                    view.mostrarErro("Opção inválida");
            }
        }
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

        supportTicketService.addTicket(currentUser, title, message);
        view.mostrarTicket(currentUser.getName(), title, message);
    }
}
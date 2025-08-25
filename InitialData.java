import java.time.LocalDate;
import java.time.LocalDateTime;


/////////////////////////////////////
/// VER ESSA CLASSE AQUI ////////////
/////////////////////////////////////


// As outras classes (User, Student, Admin, Course, etc.) devem ser importadas aqui.

public class InitialData {

    /**
     * Método estático para popular o sistema com dados iniciais.
     * @param data A instância de PlatformData que será populada.
     */
    public static void load(PlatformData data) {
        // --- Planos de Assinatura ---
        SubscriptionPlan basicPlan = new BasicPlan();
        SubscriptionPlan premiumPlan = new PremiumPlan();

        // --- Usuários (Admin e Alunos) ---
        Admin admin = new Admin("Admin da Silva", "admin@academiadev.com");
        Student student1 = new Student("Ana Pereira", "ana.p@email.com", basicPlan);
        Student student2 = new Student("Bruno Costa", "bruno.c@email.com", premiumPlan);
        Student student3 = new Student("Carla Dias", "carla.d@email.com", basicPlan);
        Student student4 = new Student("Daniel Souza", "daniel.s@email.com", premiumPlan); // Aluno sem matrículas ainda

        data.addUser(admin);
        data.addUser(student1);
        data.addUser(student2);
        data.addUser(student3);
        data.addUser(student4);

        // --- Cursos ---
        Course javaBasico = new Course("Java: Primeiros Passos", "Aprenda os fundamentos do Java.", "Dr. Java", 20, DifficultyLevel.BEGINNER);
        Course javaAvancado = new Course("Java: Padrões de Projeto", "Explore os padrões de projeto com Java.", "Dr. Java", 40, DifficultyLevel.ADVANCED);
        Course pythonBasico = new Course("Python para Ciência de Dados", "Introdução ao Python com Pandas e NumPy.", "Prof. Cobra", 30, DifficultyLevel.BEGINNER);
        Course springBoot = new Course("API Rest com Spring Boot", "Construa APIs robustas com Spring.", "Prof. Mola", 35, DifficultyLevel.INTERMEDIATE);
        Course docker = new Course("Docker para Desenvolvedores", "Domine containers e facilite seus deploys.", "Prof. Container", 15, DifficultyLevel.INTERMEDIATE);
        Course dbDesign = new Course("Modelagem de Banco de Dados", "Aprenda a modelar bancos de dados relacionais.", "Sra. SQL", 25, DifficultyLevel.ADVANCED);
        
        // Curso inativo para teste da regra de negócio
        Course cursoLegado = new Course("Algoritmos em Pascal", "Curso histórico.", "Prof. Antigo", 50, DifficultyLevel.BEGINNER);
        cursoLegado.setStatus(CourseStatus.INACTIVE);

        data.addCourse(javaBasico);
        data.addCourse(javaAvancado);
        data.addCourse(pythonBasico);
        data.addCourse(springBoot);
        data.addCourse(docker);
        data.addCourse(dbDesign);
        data.addCourse(cursoLegado);

        // --- Matrículas (Enrollments) ---
        // Aluna Ana (Plano Basic) com 2 matrículas
        Enrollment enrollmentAna1 = new Enrollment(student1, javaBasico, LocalDate.now().minusDays(30));
        enrollmentAna1.updateProgress(80); // Progresso de 80%
        
        Enrollment enrollmentAna2 = new Enrollment(student1, pythonBasico, LocalDate.now().minusDays(10));
        enrollmentAna2.updateProgress(25); // Progresso de 25%

        student1.addEnrollment(enrollmentAna1);
        student1.addEnrollment(enrollmentAna2);

        // Aluno Bruno (Plano Premium) com 3 matrículas, para testar quem tem mais matrículas
        Enrollment enrollmentBruno1 = new Enrollment(student2, javaAvancado, LocalDate.now().minusDays(60));
        enrollmentBruno1.updateProgress(100); // Curso concluído
        
        Enrollment enrollmentBruno2 = new Enrollment(student2, springBoot, LocalDate.now().minusDays(20));
        enrollmentBruno2.updateProgress(50);
        
        Enrollment enrollmentBruno3 = new Enrollment(student2, docker, LocalDate.now().minusDays(5));
        enrollmentBruno3.updateProgress(10);

        student2.addEnrollment(enrollmentBruno1);
        student2.addEnrollment(enrollmentBruno2);
        student2.addEnrollment(enrollmentBruno3);
        
        // Aluna Carla (Plano Basic) com 1 matrícula
        Enrollment enrollmentCarla1 = new Enrollment(student3, dbDesign, LocalDate.now().minusDays(15));
        enrollmentCarla1.updateProgress(45);
        student3.addEnrollment(enrollmentCarla1);
        
        // --- Tickets de Suporte (para popular a fila) ---
        SupportTicket ticket1 = new SupportTicket(student1, "Dúvida sobre Loop em Java", "Não estou conseguindo fazer o exercício da aula 5.", LocalDateTime.now().minusHours(2));
        SupportTicket ticket2 = new SupportTicket(student3, "Erro na instalação do Python", "O pip não está funcionando no meu computador.", LocalDateTime.now().minusHours(1));

        data.addSupportTicket(ticket1);
        data.addSupportTicket(ticket2);
    }
}
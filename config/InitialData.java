
import model.Admin;
import model.BasicPlan;
import model.Course;
import model.CourseStatus;
import model.DifficultyLevel;
import model.Enrollment;
import model.PremiumPlan;
import model.Student;
import model.SubscriptionPlan;
import model.SupportTicket;


import repository.CourseRepository;
import repository.SupportTicketRepository;
import repository.UserRepository;
import repository.EnrollmentRepository;
/////////////////////////////////////
/// VER ESSA CLASSE AQUI ////////////
/////////////////////////////////////


// As outras classes (User, Student, Admin, Course, etc.) devem ser importadas aqui.

public class InitialData {

    /**
     * Método estático para popular o sistema com dados iniciais.
     * @param data A instância de PlatformData que será populada.
     */
    public static void load(UserRepository users, CourseRepository courses, SupportTicketRepository supportTickets, EnrollmentRepository enrollments) {
        // --- Planos de Assinatura ---
        SubscriptionPlan basicPlan = new BasicPlan();
        SubscriptionPlan premiumPlan = new PremiumPlan();

        // --- Usuários (Admin e Alunos) ---
        Admin admin = new Admin("Admin da Silva", "admin@academiadev.com");
        Student student1 = new Student("Ana Pereira", "ana.p@email.com", basicPlan);
        Student student2 = new Student("Bruno Costa", "bruno.c@email.com", premiumPlan);
        Student student3 = new Student("Carla Dias", "carla.d@email.com", basicPlan);
        Student student4 = new Student("Daniel Souza", "daniel.s@email.com", premiumPlan); // Aluno sem matrículas ainda

        users.save(admin);
        users.save(student1);
        users.save(student2);
        users.save(student3);
        users.save(student4);

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

        courses.save(javaBasico);
        courses.save(javaAvancado);
        courses.save(pythonBasico);
        courses.save(springBoot);
        courses.save(docker);
        courses.save(dbDesign);
        courses.save(cursoLegado);

        // --- Matrículas (Enrollments) ---
        // Aluna Ana (Plano Basic) com 2 matrículas
        Enrollment enrollmentAna1 = new Enrollment(student1, javaBasico);
        enrollmentAna1.updateProgress(80); // Progresso de 80%
        
        Enrollment enrollmentAna2 = new Enrollment(student1, pythonBasico);
        enrollmentAna2.updateProgress(25); // Progresso de 25%

        enrollments.save(enrollmentAna1);
        enrollments.save(enrollmentAna2);

        // Aluno Bruno (Plano Premium) com 3 matrículas, para testar quem tem mais matrículas
        Enrollment enrollmentBruno1 = new Enrollment(student2, javaAvancado);
        enrollmentBruno1.updateProgress(100); // Curso concluído
        
        Enrollment enrollmentBruno2 = new Enrollment(student2, springBoot);
        enrollmentBruno2.updateProgress(50);
        
        Enrollment enrollmentBruno3 = new Enrollment(student2, docker);
        enrollmentBruno3.updateProgress(10);

        enrollments.save(enrollmentBruno1);
        enrollments.save(enrollmentBruno2);
        enrollments.save(enrollmentBruno3);
        
        // Aluna Carla (Plano Basic) com 1 matrícula
        Enrollment enrollmentCarla1 = new Enrollment(student3, dbDesign);
        enrollmentCarla1.updateProgress(45);
        enrollments.save(enrollmentCarla1);
        
        // --- Tickets de Suporte (para popular a fila) ---
        SupportTicket ticket1 = new SupportTicket(student1, "Dúvida sobre Loop em Java", "Não estou conseguindo fazer o exercício da aula 5.");
        SupportTicket ticket2 = new SupportTicket(student3, "Erro na instalação do Python", "O pip não está funcionando no meu computador.");

        supportTickets.save(ticket1);
        supportTickets.save(ticket2);
    }
}
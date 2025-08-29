package service;

import dtos.EnrollmentDTO;
import model.*;
import repository.EnrollmentRepository;
import repository.CourseRepository;
import repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public EnrollmentService(
            EnrollmentRepository enrollmentRepository,
            CourseRepository courseRepository,
            UserRepository userRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    /**
     * Matricula um aluno em um curso.
     * @param studentEmail email do aluno
     * @param courseTitle título do curso
     * @return EnrollmentDTO da matrícula criada
     */
    public EnrollmentDTO enrollStudentInCourse(String studentEmail, String courseTitle) {
    // 1. Busca aluno
    Student student = (Student) userRepository.findByEmail(studentEmail)
            .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado: " + studentEmail));

    // 2. Busca curso
    Course course = courseRepository.findByTitle(courseTitle)
            .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado: " + courseTitle));

    // 3. Valida se o curso está ativo
    if (course.getStatus() != CourseStatus.ACTIVE) {
        throw new IllegalArgumentException("O curso está inativo e não permite matrículas.");
    }

    // 4. EVITA MATRÍCULA DUPLICADA → PRIMEIRO!
    if (enrollmentRepository.existsByStudentAndCourse(student, courseTitle)) {
        throw new IllegalArgumentException("Aluno já está matriculado neste curso.");
    }

    // 5. Valida limite do plano
    int currentEnrollments = enrollmentRepository.findByStudent(student).size();
    if (!student.getSubscriptionPlan().canEnroll(currentEnrollments)) {
        throw new IllegalArgumentException("Seu plano não permite mais matrículas.");
    }

    // 6. Cria nova matrícula
    Enrollment enrollment = new Enrollment(student, course);

    // 7. SÓ AGORA salva
    enrollmentRepository.save(enrollment);

    // 8. Retorna DTO
    return toDTO(enrollment);
}

    /**
     * Cancela a matrícula de um aluno em um curso.
     * @param studentEmail email do aluno
     * @param courseTitle título do curso
     */
    public void cancelEnrollment(String studentEmail, String courseTitle) {
        Student student = (Student) userRepository.findByEmail(studentEmail)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado."));

        Enrollment enrollment = enrollmentRepository.findByStudentAndCourse(student, courseTitle)
                .orElseThrow(() -> new IllegalArgumentException(
                    "Matrícula não encontrada: aluno não está matriculado no curso '" + courseTitle + "'"));

        enrollmentRepository.delete(enrollment);
    }

    /**
     * Atualiza o progresso de uma matrícula.
     * @param studentEmail email do aluno
     * @param courseTitle título do curso
     * @param progress novo progresso (0 a 100)
     */

    public EnrollmentDTO updateProgress(String studentEmail, String courseTitle, int progress) {
    Student student = (Student) userRepository.findByEmail(studentEmail)
            .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado."));

    Enrollment enrollment = enrollmentRepository.findByStudentAndCourse(student, courseTitle)
            .orElseThrow(() -> new IllegalArgumentException(
                "Matrícula não encontrada: " + studentEmail + " no curso " + courseTitle));

    
    enrollment.setProgress(progress); 
    return toDTO(enrollment);
}

   
    public List<EnrollmentDTO> getEnrollmentsByStudent(String studentEmail) {
        Student student = (Student) userRepository.findByEmail(studentEmail)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado."));

        return enrollmentRepository.findByStudent(student).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    
    public List<EnrollmentDTO> getEnrollmentsByCourse(String courseTitle) {
        Course course = courseRepository.findByTitle(courseTitle)
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado."));

        return enrollmentRepository.findByCourse(course).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    
    private EnrollmentDTO toDTO(Enrollment enrollment) {
        EnrollmentDTO dto = new EnrollmentDTO();
        dto.setStudentName(enrollment.getStudent().getName());
        dto.setStudentEmail(enrollment.getStudent().getEmail());
        dto.setCourseTitle(enrollment.getCourse().getTitle());
        dto.setCourseDescription(enrollment.getCourse().getDescription());
        dto.setInstructorName(enrollment.getCourse().getInstructorName());
        dto.setDurationInHours(enrollment.getCourse().getDurationInHours());
        dto.setDifficultyLevel(enrollment.getCourse().getDifficultyLevel());
        dto.setCourseStatus(enrollment.getCourse().getStatus());
        dto.setProgress(enrollment.getProgress());
        return dto;
    }
}
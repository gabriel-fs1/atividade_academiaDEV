package service;

import dtos.EnrollmentDTO;
import dtos.EnrollmentExportDTO;
import model.*;
import repository.EnrollmentRepository;
import repository.CourseRepository;
import repository.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import Exceptions.CourseNotFoundException;
import Exceptions.EnrollmentException;
import Exceptions.UserNotFoundException;

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

    
    public EnrollmentDTO enrollStudentInCourse(String studentEmail, String courseTitle) {
 
    Student student = (Student) userRepository.findByEmail(studentEmail)
            .orElseThrow(() -> new UserNotFoundException("Aluno não encontrado: " + studentEmail));

    Course course = courseRepository.findByTitle(courseTitle)
            .orElseThrow(() -> new CourseNotFoundException("Curso não encontrado: " + courseTitle));

    if (course.getStatus() != CourseStatus.ACTIVE) {
        throw new IllegalArgumentException("O curso está inativo e não permite matrículas.");
    }

    if (enrollmentRepository.existsByStudentAndCourse(student, courseTitle)) {
        throw new IllegalArgumentException("Aluno já está matriculado neste curso.");
    }

    int currentEnrollments = enrollmentRepository.findByStudent(student).size();
    if (!student.getSubscriptionPlan().canEnroll(currentEnrollments)) {
        throw new IllegalArgumentException("Seu plano não permite mais matrículas.");
    }

    Enrollment enrollment = new Enrollment(student, course);

    enrollmentRepository.save(enrollment);

    return toDTO(enrollment);
}


    public void cancelEnrollment(String studentEmail, String courseTitle) {
        Student student = (Student) userRepository.findByEmail(studentEmail)
                .orElseThrow(() -> new UserNotFoundException("Aluno não encontrado."));

        Enrollment enrollment = enrollmentRepository.findByStudentAndCourse(student, courseTitle)
                .orElseThrow(() -> new EnrollmentException(
                    "Matrícula não encontrada: aluno não está matriculado no curso '" + courseTitle + "'"));

        enrollmentRepository.delete(enrollment);
    }


    public EnrollmentDTO updateProgress(String studentEmail, String courseTitle, int progress) {
    Student student = (Student) userRepository.findByEmail(studentEmail)
            .orElseThrow(() -> new UserNotFoundException("Aluno não encontrado."));

    Enrollment enrollment = enrollmentRepository.findByStudentAndCourse(student, courseTitle)
            .orElseThrow(() -> new EnrollmentException(
                "Matrícula não encontrada: " + studentEmail + " no curso " + courseTitle));

    
    enrollment.setProgress(progress); 
    return toDTO(enrollment);
}

   
    public List<EnrollmentDTO> getEnrollmentsByStudent(String studentEmail) {
        Student student = (Student) userRepository.findByEmail(studentEmail)
                .orElseThrow(() -> new UserNotFoundException("Aluno não encontrado."));

        return enrollmentRepository.findByStudent(student).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    
    public List<EnrollmentDTO> getEnrollmentsByCourse(String courseTitle) {
        Course course = courseRepository.findByTitle(courseTitle)
                .orElseThrow(() -> new CourseNotFoundException("Curso não encontrado."));

        return enrollmentRepository.findByCourse(course).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Collection<EnrollmentExportDTO> getAllEnrollmentsForExport() {
        return enrollmentRepository.findAll().stream()
                .map(this::toExportDTO)
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

    private EnrollmentExportDTO toExportDTO(Enrollment e) {
        EnrollmentExportDTO dto = new EnrollmentExportDTO();
        dto.setStudent(e.getStudent().getName());
        dto.setEmail(e.getStudent().getEmail());
        dto.setCourse(e.getCourse().getTitle());
        dto.setProgress(e.getProgress());
        return dto;
    
    }
}
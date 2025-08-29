package service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import dtos.CourseCatalogDTO;
import dtos.UserSummaryDTO;
import model.Course;
import model.CourseStatus;
import model.DifficultyLevel;
import model.Enrollment;
import model.Student;
import repository.CourseRepository;
import repository.EnrollmentRepository;
import repository.UserRepository;

public class ReportService {

    //Classe para fazer o relatório e analise

    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;

    public ReportService(CourseRepository courseRepo,
                         EnrollmentRepository enrollmentRepo,
                         UserRepository userRepo) {
        this.courseRepository = courseRepo;
        this.enrollmentRepository = enrollmentRepo;
        this.userRepository = userRepo;
    }

    public List<CourseCatalogDTO> getCoursesByDifficulty(DifficultyLevel level) {
        return courseRepository.findAll().stream()
                .filter(course -> course.getDifficultyLevel() == level)
                .filter(course -> course.getStatus() == CourseStatus.ACTIVE)
                .sorted(Comparator.comparing(Course::getTitle))
                .map(this::toCourseCatalogDTO)
                .collect(Collectors.toList());
    }

    public Set<String> getActiveInstructors() {
        return courseRepository.findAll().stream()
                .filter(course -> course.getStatus() == CourseStatus.ACTIVE)
                .map(Course::getInstructorName)
                .collect(Collectors.toSet());
    }

    public Map<String, List<UserSummaryDTO>> getStudentsByPlan() {
        return userRepository.findAll().stream()
                .filter(user -> user instanceof Student)
                .map(user -> (Student) user)
                .collect(Collectors.groupingBy(
                    student -> student.getSubscriptionPlan().getPlanName(),
                    Collectors.mapping(
                        student -> new UserSummaryDTO(student.getName(), student.getEmail(), "STUDENT"),
                        Collectors.toList()
                    )
                ));
    }


    public double getAverageProgress() {
        return enrollmentRepository.findAll().stream()
                .mapToInt(Enrollment::getProgress)
                .average()
                .orElse(0.0);
    }


    public Optional<UserSummaryDTO> getStudentWithMostEnrollments() {
        return userRepository.findAll().stream()
                .filter(user -> user instanceof Student)
                .map(user -> (Student) user)
                .max(Comparator.comparingInt(
                    student -> enrollmentRepository.findByStudent(student).size()
                ))
                .map(this::toUserSummaryDTO);
    }

    private CourseCatalogDTO toCourseCatalogDTO(Course course) {
        CourseCatalogDTO dto = new CourseCatalogDTO();
        dto.setTitle(course.getTitle());
        dto.setInstructorName(course.getInstructorName());
        dto.setDifficultyLevel(course.getDifficultyLevel());
        dto.setDurationInHours(course.getDurationInHours());
        dto.setStatus(course.getStatus());
        return dto;
    }

    private UserSummaryDTO toUserSummaryDTO(Student student) {
        return new UserSummaryDTO(student.getName(), student.getEmail(), "STUDENT");
    }
}
    


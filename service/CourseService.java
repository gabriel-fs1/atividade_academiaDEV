package service;

import dtos.CourseCatalogDTO;
import model.Course;
import model.CourseStatus;
import model.DifficultyLevel;
import repository.CourseRepository;

import java.util.Collection;
import java.util.Optional;

public class CourseService {

    private final CourseRepository cr;

    public CourseService(CourseRepository cr) {
        this.cr = cr;
    }

    public CourseCatalogDTO addCourse(String title, String description,
     String instructorName, int durationInHours, DifficultyLevel difficultyLevel) {

        Course savedCourse = new Course(title, description,
         instructorName, durationInHours, difficultyLevel);

        this.cr.save(savedCourse);
        return toDTO(savedCourse);
    }

    public Collection<CourseCatalogDTO> findAll() {
        return this.cr.findAll().stream().map(this::toDTO).toList();
    }

    public Optional<CourseCatalogDTO> findByTitle(String title) {
        return this.cr.findByTitle(title).map(this::toDTO);
    }

    public Collection<CourseCatalogDTO> findByDifficultyLevel(DifficultyLevel level) {
        return this.cr.findByDifficultyLevel(level).stream().map(this::toDTO).toList();
    }

    public Collection<CourseCatalogDTO> findActiveCourses() {
        return this.cr.findActiveCourses().stream().map(this::toDTO).toList();
    }

    public CourseCatalogDTO updateCourseStatus(String title, CourseStatus newStatus) {
    Course course = cr.findByTitle(title)
            .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado: " + title));

    course.setStatus(newStatus); // altera o status
    cr.save(course); // salva de volta (em memória, só pra seguir o padrão)

    return toDTO(course);
}

    private CourseCatalogDTO toDTO(Course course) {
        CourseCatalogDTO ccDto = new CourseCatalogDTO();
        ccDto.setTitle(course.getTitle());
        ccDto.setDescription(course.getDescription());
        ccDto.setInstructorName(course.getInstructorName());
        ccDto.setDifficultyLevel(course.getDifficultyLevel());
        ccDto.setDurationInHours(course.getDurationInHours());
        ccDto.setStatus(course.getStatus());
        return ccDto;
    }
    
}

package repository;
import java.util.Collection;
import java.util.Optional;

import model.Course;
import model.DifficultyLevel;

public interface CourseRepository {

    void save(Course course);
    Optional<Course> findByTitle(String title);
    Collection<Course> findAll();
    Collection<Course> findByDifficultyLevel(DifficultyLevel level);
    Collection<Course> findActiveCourses();
    
}

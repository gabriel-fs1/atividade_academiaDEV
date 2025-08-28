package repository;
import java.util.Collection;
import java.util.Optional;

import model.Course;

public interface CourseRepository {

    void save(Course course);
    Optional<Course> findByTitle(String title);
    Collection<Course> findAll();
    
}

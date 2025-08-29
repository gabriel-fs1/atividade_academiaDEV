package repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import model.Enrollment;
import model.Student;
import model.Course;

public interface EnrollmentRepository {

    void save(Enrollment enrollment);
    void delete(Enrollment enrollment);
    
    Optional<Enrollment> findByStudentAndCourse(Student student, String courseTitle);
    List<Enrollment> findByStudent(Student student);
    List<Enrollment> findByCourse(Course course);
    
    Collection<Enrollment> findAll();
    boolean existsByStudentAndCourse(Student student, String courseTitle);

    
}

package repository;

import java.util.List;
import java.util.Optional;

import model.Enrollment;
import model.Student;
import model.Course;

public interface EnrollmentRepository {

    void save(Enrollment enrollment);
    void deleteByStudentAndCourse(Student student, Course course);
    Optional<Enrollment> findByStudentAndCourse(Student student, Course course);
    List<Enrollment> findAllByStudent(Student student);
    List<Enrollment> findAll();

    
}

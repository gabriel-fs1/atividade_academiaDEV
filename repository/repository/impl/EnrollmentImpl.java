package repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import model.Course;
import model.Enrollment;
import model.Student;
import repository.EnrollmentRepository;

public class EnrollmentImpl implements EnrollmentRepository {

    private final List<Enrollment> enrollments = new ArrayList<>();

    @Override
    public void save(Enrollment enrollment) {
        enrollments.add(enrollment);
    }

    @Override
    public void deleteByStudentAndCourse(Student student, Course course) {
        
        findByStudentAndCourse(student, course)
            .ifPresent(enrollments::remove); 
    }

    @Override
    public Optional<Enrollment> findByStudentAndCourse(Student student, Course course) {
        return enrollments.stream()
                .filter(e -> e.getStudent().equals(student) && e.getCourse().equals(course))
                .findFirst();
    }

    @Override
    public List<Enrollment> findAllByStudent(Student student) {
        return enrollments.stream()
                .filter(e -> e.getStudent().equals(student))
                .collect(Collectors.toList());
    }

    @Override
    public List<Enrollment> findAll() {
        return new ArrayList<>(enrollments);
    }
}
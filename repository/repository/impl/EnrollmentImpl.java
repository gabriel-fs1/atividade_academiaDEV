package repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import model.Course;
import model.Enrollment;
import model.Student;
import repository.EnrollmentRepository;
import java.util.Collection;

public class EnrollmentImpl implements EnrollmentRepository {

    private final List<Enrollment> enrollments = new ArrayList<>();

    @Override
    public void save(Enrollment enrollment) {
        if (enrollment == null) {
            throw new IllegalArgumentException("Matrícula não pode ser nula.");
        }
        enrollments.add(enrollment);
    }

    @Override
    public void delete(Enrollment enrollment) {
        enrollments.remove(enrollment);
    }

    @Override
public Optional<Enrollment> findByStudentAndCourse(Student student, String courseTitle) {
    System.out.println("Buscando matrícula: aluno=" + student.getName() + ", curso=" + courseTitle);
    

    return enrollments.stream()
            .filter(e -> e.getStudent().getEmail().equals(student.getEmail()))
            .filter(e -> e.getCourse().getTitle().equals(courseTitle))
            .findFirst();
}

    @Override
    public List<Enrollment> findByStudent(Student student) {
        return enrollments.stream()
                .filter(e -> e.getStudent().getEmail().equals(student.getEmail()))
                .toList();
    }

    @Override
    public List<Enrollment> findByCourse(Course course) {
        return enrollments.stream()
                .filter(e -> e.getCourse().getTitle().equals(course.getTitle()))
                .toList();
    }

    @Override
    public Collection<Enrollment> findAll() {
        return new ArrayList<>(enrollments); // cópia segura
    }

    @Override
    public boolean existsByStudentAndCourse(Student student, String courseTitle) {
        return findByStudentAndCourse(student, courseTitle).isPresent();
    }
}
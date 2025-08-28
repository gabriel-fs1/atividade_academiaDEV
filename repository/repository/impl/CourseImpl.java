package repository.impl;

import java.util.Map;
import java.util.HashMap;
import java.util.Collection;
import java.util.Optional;

import repository.CourseRepository;
import model.Course;

public class CourseImpl implements CourseRepository {

    private Map<String, Course> courses = new HashMap<>();

    @Override
    public void save(Course course) {
        courses.put(course.getTitle(), course);
    }

    @Override
    public Optional<Course> findByTitle(String title) {
        return Optional.ofNullable(courses.get(title));
    }

    @Override
    public Collection<Course> findAll() {
        return courses.values();
    }
    
}

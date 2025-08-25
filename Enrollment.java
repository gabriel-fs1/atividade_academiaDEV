import java.time.LocalDate;

public class Enrollment {

    private Student student;
    private Course course;
    private LocalDate enrollmentDate;
    private int progress;

    public Enrollment(Student student, Course course, LocalDate enrollmentDate) {
        this.student = student;
        this.course = course;
        this.enrollmentDate = enrollmentDate;
        this.progress = 0;
    }
    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public int getProgress() {
        return progress;
    }

    // tem que ver isso aqui
    public void updateProgress(int progress) {
        
    }
    
}

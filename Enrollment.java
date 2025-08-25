import java.time.LocalDate;

public class Enrollment {

    private Student student;
    private Course course;
    private LocalDate enrollmentDate;
    private int progress;

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
    public void uptadeProgress(int progress) {
        this.progress = progress;
    }
    
}

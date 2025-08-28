package model;

public class Enrollment {

    private Student student;
    private Course course;
    private int progress;

    public Enrollment(Student student, Course course) {
        this.student = student;
        this.course = course;
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
        if(progress >= 0 && progress <= 100) {
            this.progress = progress;
        }
    }
    
}

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

    public Enrollment(){}

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
    if (progress < 0 || progress > 100) {
        throw new IllegalArgumentException("O progresso deve estar entre 0 e 100%.");
    }
    if (this.progress > progress) {
        throw new IllegalArgumentException("O progresso não pode ser menor que o atual (" + this.progress + "%).");
    }
    this.progress = progress;
}

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    
    
}

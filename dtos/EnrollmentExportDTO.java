package dtos;

import annotation.CsvColumn;

public class EnrollmentExportDTO {

    @CsvColumn(header = "Aluno")
    private String student;
    @CsvColumn(header = "Email")
    private String email;
    @CsvColumn(header = "Curso")
    private String course;
    @CsvColumn(header = "Progresso")
    private int progress;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    

    public String getStudent() {
        return student;
    }

    public String getCourse() {
        return course;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
    this.progress = progress;
}

    public void setCourse(String course) {
        this.course = course;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    
}

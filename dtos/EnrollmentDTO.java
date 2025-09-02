package dtos;

import model.CourseStatus;
import model.DifficultyLevel;

public class EnrollmentDTO {

    private String studentName;
    private String studentEmail;
    private String courseTitle;
    private String courseDescription;
    private String instructorName;
    private int durationInHours;
    private DifficultyLevel difficultyLevel;
    private CourseStatus courseStatus;
    private int progress; 


    public EnrollmentDTO() {}


    public EnrollmentDTO(String studentName, String studentEmail, String courseTitle,
                         String courseDescription, String instructorName, int durationInHours,
                         DifficultyLevel difficultyLevel, CourseStatus courseStatus, int progress) {
        this.studentName = studentName;
        this.studentEmail = studentEmail;
        this.courseTitle = courseTitle;
        this.courseDescription = courseDescription;
        this.instructorName = instructorName;
        this.durationInHours = durationInHours;
        this.difficultyLevel = difficultyLevel;
        this.courseStatus = courseStatus;
        this.progress = progress;
    }

    // Getters e Setters
    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public int getDurationInHours() {
        return durationInHours;
    }

    public void setDurationInHours(int durationInHours) {
        this.durationInHours = durationInHours;
    }

    public DifficultyLevel getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(DifficultyLevel difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public CourseStatus getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(CourseStatus courseStatus) {
        this.courseStatus = courseStatus;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    @Override
    public String toString(){
        return "===============================================" +
                "\n||Nome do Estudante:'" + studentName + '\'' +
                "\n||Titulo:'" + courseTitle + '\'' +
                "\n||Descriçao:'" + courseDescription + '\'' +
                "\n||Instrutor:'" + instructorName + '\'' +
                "\n||Duração:" + durationInHours + "h" +
                "\n||Dificuldade:" + difficultyLevel +
                "\n||Progresso:" + progress + "%" +
                "\n===============================================\n" +
                "\n";
    }
}
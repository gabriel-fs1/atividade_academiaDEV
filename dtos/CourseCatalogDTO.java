package dtos;

import model.CourseStatus;
import model.DifficultyLevel;

public class CourseCatalogDTO {

    private String title;
    private String description;
    private String instructorName;
    private DifficultyLevel difficultyLevel;
    private int durationInHours;
    private CourseStatus status;

    

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public DifficultyLevel getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(DifficultyLevel difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public int getDurationInHours() {
        return durationInHours;
    }

    public void setDurationInHours(int durationInHours) {
        this.durationInHours = durationInHours;
    }

    public CourseStatus getStatus() {
        return status;
    }

    public void setStatus(CourseStatus status) {
        this.status = status;
    }

    @Override
public String toString() {
    return "===============================================\n" +
            "||Titulo:'" + title + '\'' +
            "\n||Descrição:'" + description + '\'' +
            "\n||Instrutor:'" + instructorName + '\'' +
            "\n||Duração:" + durationInHours + "h" +
            "\n||Dificuldade:" + difficultyLevel +
            "\n||Status: " + status +
            "\n===============================================\n" +
            "\n";
}
    
}

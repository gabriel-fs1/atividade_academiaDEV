package dtos;

import annotation.CsvColumn;
import model.CourseStatus;
import model.DifficultyLevel;

public class CourseExportDTO {

    @CsvColumn(header = "Titulo")
    private String title;
    @CsvColumn(header = "Descrição")
    private String description;
    @CsvColumn(header = "Instrutor")
    private String instructorName;
    @CsvColumn(header = "Duracao")
    private int durationInHours;
    @CsvColumn(header = "Dificuldade")
    private DifficultyLevel difficultyLevel;
    @CsvColumn(header = "Status")
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
    
}

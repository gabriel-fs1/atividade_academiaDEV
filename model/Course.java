package model;

import annotation.CsvColumn;

public class Course {

    @CsvColumn(header = "Titulo")
    private String title;
    @CsvColumn(header = "Descrição")
    private String description;
    @CsvColumn(header = "Instructor")
    private String instructorName;
    @CsvColumn(header = "Duracao")
    private int durationInHours;
    @CsvColumn(header = "Dificuldade")
    private DifficultyLevel difficultyLevel;
    @CsvColumn(header = "Status")
    private CourseStatus status;

    public Course(String title, String description, String instructorName, int durationInHours, DifficultyLevel difficultyLevel) {
        this.title = title;
        this.description = description;
        this.instructorName = instructorName;
        this.durationInHours = durationInHours;
        this.difficultyLevel = difficultyLevel;
        this.status = CourseStatus.ACTIVE;
    }

    public String getTitle() {
        return title;
    }

    public String getInstructorName(){
        return instructorName;
    }

    public int getDurationInHours(){
        return durationInHours;
    }

    public String getDescription(){
        return description;
    }

    public DifficultyLevel getDifficultyLevel(){
        return difficultyLevel;
    }

    public CourseStatus getStatus(){
        return status;
    }

    public void setStatus(CourseStatus status){
        this.status = status;
    }
    
    
}

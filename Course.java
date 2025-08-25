public class Course {

    private String title;
    private String description;
    private String instructorName;
    private int durationInHours;
    private DifficultyLevel difficultyLevel;
    private CourseStatus status;

    public String getTitle() {
        return title;
    }

    public String getIntructorName(){
        return instructorName;
    }

    public DifficultyLevel getDifficultyLevel(){
        return difficultyLevel;
    }

    public CourseStatus getStatus(){
        return status;
    }

    //tem que ver isso aqui mais tarde
    public void setStatus(CourseStatus status){
        this.status = status;
    }
    
    
}

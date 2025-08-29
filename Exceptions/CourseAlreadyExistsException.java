package Exceptions;

public class CourseAlreadyExistsException extends RuntimeException {

    public CourseAlreadyExistsException(String message) {
        super(message);
    }
    
}

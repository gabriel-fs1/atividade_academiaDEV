import java.time.LocalDateTime;

public class SupportTicket {

    private User user;
    private String title;
    private String message;
    private LocalDateTime submissionDate;

    public User getUser() {
        return user;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }
    
}

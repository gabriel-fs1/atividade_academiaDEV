import java.time.LocalDateTime;

public class SupportTicket {

    private User user;
    private String title;
    private String message;
    private LocalDateTime submissionDate;

    public SupportTicket(User user, String title, String message, LocalDateTime submissionDate) {
        this.user = user;
        this.title = title;
        this.message = message;
        this.submissionDate = submissionDate;
    }

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

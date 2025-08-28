package model;


public class SupportTicket {

    private User user;
    private String title;
    private String message;


    public SupportTicket(User user, String title, String message) {
        this.user = user;
        this.title = title;
        this.message = message;

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

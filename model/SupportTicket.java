package model;

public class SupportTicket {

    private User user;
    private String title;
    private String description;


    public SupportTicket(User user, String title, String description) {
        this.user = user;
        this.title = title;
        this.description = description;

    }

    public User getUser() {
        return user;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
    
}

package dtos;

public class SupportTicketDTO {

    private String title;
    private String description;
    private String emailAuthor;

    public SupportTicketDTO(String emailAuthor, String title, String description) {
        this.emailAuthor = emailAuthor;
        this.title = title;
        this.description = description;
    }

    public SupportTicketDTO() {
        
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getEmailAuthor() {
        return emailAuthor;
    }

    public void setEmailAuthor(String emailAuthor) {
        this.emailAuthor = emailAuthor;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
}

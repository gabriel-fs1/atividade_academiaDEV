package model;

import annotation.CsvColumn;

public class SupportTicket {

    @CsvColumn(header = "Usuário")
    private User user;
    @CsvColumn(header = "Titulo")
    private String title;
    @CsvColumn(header = "Descrição")
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

package dtos;

import annotation.CsvColumn;


public class TicketExportDTO {

    @CsvColumn(header = "Usuário")
    private String user;
    @CsvColumn(header = "Email")
    private String email;
    @CsvColumn(header = "Titulo")
    private String title;
    @CsvColumn(header = "Descrição")
    private String description;
    
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
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
}

package dtos;

import annotation.CsvColumn;

public class UserExportDTO {

    @CsvColumn(header = "Nome")
    protected String name;
    @CsvColumn(header = "Email")
    protected String email;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
}

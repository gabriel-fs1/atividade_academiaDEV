package dtos;

public class UserSummaryDTO {
   
    private String name;
    private String email;
    private String role;

    public UserSummaryDTO() {}

    public UserSummaryDTO(String name, String email, String role) {
        this.name = name;
        this.email = email;
        this.role = role;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "" +
                "| name:'" + name + '\'' +
                ", email:'" + email + '\'' +
                " | ";
    }
  
}

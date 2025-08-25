import java.util.Map;
import java.util.Queue;
import java.util.Optional;

public class PlatformData {

    private Map<String, User> users;
    private Map<String, Course> courses;
    private Queue<SupportTicket> supportTickets;
    

    // ver isso
    public void addUser(User user) {
        users.put(user.getUsername(), user);
    }

    // ver isso tbm
    public Optional<User> findUserByEmail(String email) {
        return users.values().stream().filter(user -> user.getEmail().equals(email)).findFirst();
    }
}

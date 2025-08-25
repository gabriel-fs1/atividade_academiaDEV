import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Optional;

public class PlatformData {

    private Map<String, User> users;
    private Map<String, Course> courses;
    private Queue<SupportTicket> supportTickets;

    public PlatformData() {
        this.users = new HashMap<>();
        this.courses = new HashMap<>();
        this.supportTickets = new LinkedList<>();
    }
    
    public Map<String, User> getUsers() {
        return users;
    }

    public Map<String, Course> getCourses() {
        return courses;
    }

    public Queue<SupportTicket> getSupportTickets() {
        return supportTickets;
    }

    // ver isso
    public void addUser(User user) {
        if (user != null && user.getEmail() != null) {
            users.put(user.getEmail(), user);
        }
    }

    public void addCourse(Course course) {
        if (course != null && course.getTitle() != null) {
            courses.put(course.getTitle(), course);
        }
    }

    public void addSupportTicket(SupportTicket ticket) {
        if (ticket != null) {
            supportTickets.add(ticket);
        }
    }

    

    // ver isso tbm
    public Optional<User> findUserByEmail(String email) {
        return users.values().stream().filter(user -> user.getEmail().equals(email)).findFirst();
    }
}

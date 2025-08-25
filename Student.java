import java.util.List;

public class Student extends User{

    private SubscriptionPlan subscriptionPlan;
    private List<Enrollment> enrollments;

    public Student(String username, String password, SubscriptionPlan subscriptionPlan) {
        super(username, password);
        this.subscriptionPlan = subscriptionPlan;
    }

    public SubscriptionPlan getSubscriptionPlan() {
        return subscriptionPlan;
    }

    public void setSubscriptionPlan(SubscriptionPlan subscriptionPlan) {
        this.subscriptionPlan = subscriptionPlan;
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    // ver isso
    public void addEnrollment(Enrollment enrollment) {
        enrollments.add(enrollment);
    }

    // tem que ver isso aqui tbm
    public void removeEnrollment(Course course) {
        
    }
    
}

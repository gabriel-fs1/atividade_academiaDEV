package model;
import java.util.List;
import java.util.ArrayList;

public class Student extends User{

    private SubscriptionPlan subscriptionPlan;
    private List<Enrollment> enrollments;

    public Student(String name, String email, SubscriptionPlan subscriptionPlan) {
        super(name, email);
        this.subscriptionPlan = subscriptionPlan;
        this.enrollments = new ArrayList<>();
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
       this.enrollments.add(enrollment);
    }

    // tem que ver isso aqui tbm
    public void removeEnrollment(Course course) {
        this.enrollments.removeIf(enrollment -> enrollment.getCourse().equals(course));
    }
    
}

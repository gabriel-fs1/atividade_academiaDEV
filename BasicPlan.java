public class BasicPlan implements SubscriptionPlan {
    
    private int MAX_ENROLLMENTS = 3;
    
    public String getPlanName() {
        return "Basic Plan";
    }
    
    public boolean canEnroll(long currentEnrollments) {
        return currentEnrollments < MAX_ENROLLMENTS;
    }
    
}

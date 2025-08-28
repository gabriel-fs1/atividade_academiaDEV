package model;
public class BasicPlan implements SubscriptionPlan {
    
    private static final int MAX_ENROLLMENTS = 3;
    
    @Override
    public String getPlanName() {
        return "Basic Plan";
    }
    
    @Override
    public boolean canEnroll(long currentEnrollments) {
        return currentEnrollments < MAX_ENROLLMENTS;
    }
    
}

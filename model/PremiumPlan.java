package model;
public class PremiumPlan implements SubscriptionPlan {
    public String getPlanName() {
        return "Premium Plan";
    }

    public boolean canEnroll(long currentEnrollments) {
        return true;
    }
    
}

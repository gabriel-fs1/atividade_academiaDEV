package model;
public interface SubscriptionPlan {

    public abstract boolean canEnroll(long currentEnrollments);

    public abstract String getPlanName();

}

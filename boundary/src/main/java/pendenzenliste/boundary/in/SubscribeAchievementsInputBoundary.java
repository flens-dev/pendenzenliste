package pendenzenliste.boundary.in;

import pendenzenliste.boundary.out.SubscribeAchievementsOutputBoundary;
import pendenzenliste.boundary.out.SubscribeAchievementsResponse;

/**
 * An input boundary that can be used to subscribe to the achievements.
 */
public interface SubscribeAchievementsInputBoundary extends
    InputBoundary<SubscribeAchievementsRequest, SubscribeAchievementsResponse, SubscribeAchievementsOutputBoundary>
{
}

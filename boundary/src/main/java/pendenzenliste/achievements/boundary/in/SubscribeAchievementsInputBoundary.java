package pendenzenliste.achievements.boundary.in;

import pendenzenliste.achievements.boundary.out.SubscribeAchievementsOutputBoundary;
import pendenzenliste.achievements.boundary.out.SubscribeAchievementsResponse;
import pendenzenliste.boundary.in.InputBoundary;

/**
 * An input boundary that can be used to subscribe to the achievements.
 */
public interface SubscribeAchievementsInputBoundary extends
    InputBoundary<SubscribeAchievementsRequest, SubscribeAchievementsResponse, SubscribeAchievementsOutputBoundary>
{
}

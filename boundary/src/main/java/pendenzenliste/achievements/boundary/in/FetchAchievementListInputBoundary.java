package pendenzenliste.achievements.boundary.in;

import pendenzenliste.achievements.boundary.out.FetchAchievementListOutputBoundary;
import pendenzenliste.achievements.boundary.out.FetchAchievementListResponse;
import pendenzenliste.boundary.in.InputBoundary;

/**
 * An input boundary that can be used to fetch a list of achievements.
 */
public interface FetchAchievementListInputBoundary extends
    InputBoundary<FetchAchievementListRequest, FetchAchievementListResponse, FetchAchievementListOutputBoundary>
{
}

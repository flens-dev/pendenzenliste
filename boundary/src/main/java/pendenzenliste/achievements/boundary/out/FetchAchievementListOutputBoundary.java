package pendenzenliste.achievements.boundary.out;

import pendenzenliste.boundary.out.OutputBoundary;

/**
 * An output boundary that can be used to handle the results of a fetch achievements list request.
 */
public interface FetchAchievementListOutputBoundary
    extends OutputBoundary<FetchAchievementListOutputBoundary, FetchAchievementListResponse>
{
  /**
   * Handles a successful response.
   *
   * @param response The response.
   */
  void handleSuccessfulResponse(FetchAchievementListResponse response);
}

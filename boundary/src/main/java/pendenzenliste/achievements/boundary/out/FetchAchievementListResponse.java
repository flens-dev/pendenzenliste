package pendenzenliste.achievements.boundary.out;

import java.util.Collection;

import pendenzenliste.boundary.out.Response;

/**
 * A response that can be used to represent the result of a fetch achievement list request.
 *
 * @param achievements The achievements.
 */
public record FetchAchievementListResponse(Collection<AchievementResponseModel> achievements)
    implements Response<FetchAchievementListOutputBoundary, FetchAchievementListResponse>
{
  /**
   * {@inheritDoc}
   */
  @Override
  public void applyTo(final FetchAchievementListOutputBoundary outputBoundary)
  {
    outputBoundary.handleSuccessfulResponse(this);
  }
}

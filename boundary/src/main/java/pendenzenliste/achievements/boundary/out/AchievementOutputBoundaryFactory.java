package pendenzenliste.achievements.boundary.out;

/**
 * A factory that can be used to provide access to the various achievement output boundaries.
 */
public interface AchievementOutputBoundaryFactory
{
  /**
   * The output boundary used to subscribe to achievements.
   *
   * @return The output boundary.
   */
  SubscribeAchievementsOutputBoundary subscribe();

  /**
   * The output boundary used to list the achievements.
   *
   * @return The output boundary.
   */
  FetchAchievementListOutputBoundary list();
}

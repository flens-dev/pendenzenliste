package pendenzenliste.boundary.in;

/**
 * A factory that can be used to access the various input boundaries for achievements.
 */
public interface AchievementInputBoundaryFactory
{
  /**
   * An input boundary that can be used to subscribe to the achievements.
   *
   * @return The input boundary.
   */
  SubscribeAchievementsInputBoundary subscribe();
}

package pendenzenliste.boundary.out;

/**
 * An output boundary that can be used to handle the responses to a subscribe achievements request.
 */
public interface SubscribeAchievementsOutputBoundary
    extends OutputBoundary<SubscribeAchievementsOutputBoundary, SubscribeAchievementsResponse>
{
  /**
   * Displays the unlocked achievement.
   *
   * @param name The name.
   */
  void displayUnlockedAchievement(String name);

  /**
   * Checks whether the output boundary has been detached.
   *
   * @return True if the output boundary has been detached, otherwise false.
   */
  boolean isDetached();
}

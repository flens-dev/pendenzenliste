package pendenzenliste.achievements.boundary.out;

/**
 * A response that can be used to represent an unlocked achievement.
 *
 * @param name The name of the achievement.
 */
public record AchievementUnlockedResponse(String name) implements SubscribeAchievementsResponse
{
  /**
   * {@inheritDoc}
   */
  @Override
  public void applyTo(final SubscribeAchievementsOutputBoundary out)
  {
    out.displayUnlockedAchievement(name);
  }
}

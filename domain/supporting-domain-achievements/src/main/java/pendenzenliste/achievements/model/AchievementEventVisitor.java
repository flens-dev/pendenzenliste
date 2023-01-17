package pendenzenliste.achievements.model;

/**
 * A visitor tha can be used to visit the various achievement events.
 */
public interface AchievementEventVisitor
{
  /**
   * Visits the given event.
   *
   * @param event The event that should be visited.
   */
  void visit(AchievementUnlockedEvent event);
}

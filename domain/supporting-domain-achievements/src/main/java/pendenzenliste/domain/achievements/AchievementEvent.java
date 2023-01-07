package pendenzenliste.domain.achievements;

import java.time.LocalDateTime;

/**
 * The common interface for objects that represent an event in the context of the achievements.
 */
public interface AchievementEvent
{
  /**
   * The timestamp that indicates when the event has occurred.
   *
   * @return The timestamp.
   */
  LocalDateTime timestamp();
}

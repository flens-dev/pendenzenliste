package pendenzenliste.domain.achievements;

import java.time.LocalDateTime;

/**
 * An event that can be used to indicate that an achievement has been unlocked.
 *
 * @param timestamp The timestamp.
 * @param name      The name of the unlocked achievement.
 */
public record AchievementUnlockedEvent(LocalDateTime timestamp,
                                       AchievementValueType name) implements AchievementEvent
{
}

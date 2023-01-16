package pendenzenliste.achievements.boundary.out;


import java.time.LocalDateTime;

/**
 * A response model that can be used to represent an achievement.
 *
 * @param name     The name of the achievement.
 * @param state    The state of the achievement.
 * @param unlocked The unlocked timestamp.
 */
public record AchievementResponseModel(String name, String state, LocalDateTime unlocked)
{
}

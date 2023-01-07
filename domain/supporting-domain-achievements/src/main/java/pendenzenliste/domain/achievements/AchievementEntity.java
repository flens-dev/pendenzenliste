package pendenzenliste.domain.achievements;

/**
 * An entity that can be used to represent an achievement.
 *
 * @param name     The name of the achievement.
 * @param state    The state of the achievement.
 * @param unlocked The timestamp that indicates when the achievement has been unlocked.
 */
public record AchievementEntity(IdentityValueObject identity,
                                AchievementValueType name,
                                StateValueType state,
                                UnlockedTimestampValueType unlocked)
{
}

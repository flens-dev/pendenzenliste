package pendenzenliste.achievements.model;

import java.io.Serializable;

/**
 * An entity that can be used to represent an achievement.
 *
 * @param identity The identity of the achievement.
 * @param name     The name of the achievement.
 * @param state    The state of the achievement.
 * @param unlocked The timestamp that indicates when the achievement has been unlocked.
 */
public record AchievementEntity(IdentityValueObject identity, AchievementValueType name,
                                StateValueType state, UnlockedTimestampValueType unlocked)
        implements Serializable {

    /**
     * Unlocks the achievement.
     *
     * @return The unlocked achievement.
     */
    public AchievementEntity unlock() {
        return new AchievementEntity(identity(), name(), StateValueType.UNLOCKED,
                UnlockedTimestampValueType.now());
    }
}

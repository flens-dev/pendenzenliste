package pendenzenliste.achievements.model;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * A repository that can be used to read and write achievements.
 */
public interface AchievementRepository {
    /**
     * Finds a specific achievement by its identity.
     *
     * @param identity The identity.
     * @return The achievement.
     */
    Optional<AchievementAggregate> findById(IdentityValueObject identity);

    /**
     * Fetches all achievements.
     *
     * @return The achievements.
     */
    Stream<AchievementAggregate> fetchAll();

    /**
     * Fetches the locked achievements.
     *
     * @return The locked achievements.
     */
    Stream<AchievementAggregate> fetchLockedAchievements();

    /**
     * Stores the given achievement.
     *
     * @param achievement The achievement.
     */
    void store(final AchievementAggregate achievement);
}

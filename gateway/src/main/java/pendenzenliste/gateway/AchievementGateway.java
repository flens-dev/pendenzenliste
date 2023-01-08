package pendenzenliste.gateway;

import java.util.Optional;
import java.util.stream.Stream;

import pendenzenliste.domain.achievements.AchievementAggregate;
import pendenzenliste.domain.achievements.IdentityValueObject;

/**
 * A gateway that can be used to access the achievements.
 */
public interface AchievementGateway
{
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
   * Stores the given achievement.
   *
   * @param achievement The achievement.
   */
  void store(final AchievementAggregate achievement);
}

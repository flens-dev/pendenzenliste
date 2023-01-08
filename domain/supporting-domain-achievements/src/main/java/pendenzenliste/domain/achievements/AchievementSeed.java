package pendenzenliste.domain.achievements;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A seed for the achievements.
 */
public class AchievementSeed
{
  /**
   * The achievements that should be seeded.
   *
   * @return The achievements that should be seeded.
   */
  public static Collection<AchievementAggregate> seededAchievements()
  {

    final Collection<AchievementAggregate> achievements = new ArrayList<>();

    for (final AchievementValueType type : AchievementValueType.values())
    {
      achievements.add(AchievementAggregate.builder().identity(type.name()).name(type)
          .state(StateValueType.LOCKED).build());
    }

    return achievements;
  }
}

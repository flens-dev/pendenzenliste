package pendenzenliste.achievements.model;

import java.util.Collection;
import java.util.List;

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
    return List.of(
        DonezoAchievementAggregate.builder().state(StateValueType.LOCKED)
            .randomIdentity()
            .build(),
        ItBurnsAchievementAggregate.builder().state(StateValueType.LOCKED)
            .randomIdentity()
            .build(),
        JourneyBeginsAchievementAggregate.builder().state(StateValueType.LOCKED)
            .randomIdentity()
            .build(),
        NewYearNewMeAchievementAggregate.builder().state(StateValueType.LOCKED)
            .randomIdentity()
            .build(),
        ThirdTimesTheCharmAchievementAggregate.builder().state(StateValueType.LOCKED)
            .randomIdentity()
            .build(),
        AchievementHunterAchievementAggregate.builder().state(StateValueType.LOCKED)
            .randomIdentity()
            .build()
    );
  }
}

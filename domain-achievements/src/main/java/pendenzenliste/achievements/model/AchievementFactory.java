package pendenzenliste.achievements.model;

import java.util.Collection;
import java.util.List;

/**
 * A factory for the creation of initial achievements.
 */
public class AchievementFactory {
    /**
     * The initial achievements that should be seeded.
     *
     * @return The initial achievements that should be seeded.
     */
    public static Collection<AchievementAggregate> initialAchievements() {
        return List.of(
                DonezoAchievementAggregate.builder()
                        .state(StateValueType.LOCKED)
                        .randomIdentity()
                        .build(),
                ItBurnsAchievementAggregate.builder()
                        .state(StateValueType.LOCKED)
                        .randomIdentity()
                        .build(),
                JourneyBeginsAchievementAggregate.builder()
                        .state(StateValueType.LOCKED)
                        .randomIdentity()
                        .build(),
                NewYearNewMeAchievementAggregate.builder()
                        .state(StateValueType.LOCKED)
                        .randomIdentity()
                        .build(),
                ThirdTimesTheCharmAchievementAggregate.builder()
                        .state(StateValueType.LOCKED)
                        .randomIdentity()
                        .build(),
                AchievementHunterAchievementAggregate.builder()
                        .state(StateValueType.LOCKED)
                        .randomIdentity()
                        .build(),
                AllDoneAchievementAggregate.builder()
                        .state(StateValueType.LOCKED)
                        .randomIdentity()
                        .build(),
                LazyDogAchievementAggregate.builder()
                        .state(StateValueType.LOCKED)
                        .randomIdentity()
                        .build()
        );
    }
}

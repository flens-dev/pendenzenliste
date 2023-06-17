package pendenzenliste.achievements.model;

import java.time.LocalDateTime;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

class AchievementHunterAchievementAggregateTest
{

  @Test
  public void trackProgress_unlocked()
  {
    final var achievement = AchievementHunterAchievementAggregate.builder()
        .identity("42")
        .state(StateValueType.LOCKED)
        .build();

    for (int i = 0; i < 3; i++)
    {
      achievement.trackProgress(
          new AchievementUnlockedEvent(LocalDateTime.now(), AchievementValueType.JOURNEY_BEGINS));
    }

    final var assertions = new SoftAssertions();

    assertions.assertThat(achievement.aggregateRoot().identity())
        .isEqualTo(new IdentityValueObject("42"));
    assertions.assertThat(achievement.aggregateRoot().name())
        .isEqualTo(AchievementValueType.ACHIEVEMENT_HUNTER);
    assertions.assertThat(achievement.aggregateRoot().state())
        .isEqualTo(StateValueType.UNLOCKED);

    assertions.assertAll();
  }
}
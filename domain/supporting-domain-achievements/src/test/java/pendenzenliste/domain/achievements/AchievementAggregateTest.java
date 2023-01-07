package pendenzenliste.domain.achievements;

import java.time.LocalDateTime;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import pendenzenliste.domain.todos.ToDoCreatedEvent;

class AchievementAggregateTest
{

  @Test
  public void unlock()
  {
    final var achievement =
        AchievementAggregate.builder().identity("42").name(AchievementValueType.JOURNEY_BEGINS)
            .state(StateValueType.LOCKED)
            .build();

    achievement.unlock();

    final var assertions = new SoftAssertions();

    assertions.assertThat(achievement.aggregateRoot().identity())
        .isEqualTo(new IdentityValueObject("42"));
    assertions.assertThat(achievement.aggregateRoot().unlocked())
        .isNotNull();
    assertions.assertThat(achievement.aggregateRoot().name())
        .isEqualTo(AchievementValueType.JOURNEY_BEGINS);

    assertions.assertThat(achievement.events()).hasSize(1);
    assertions.assertThat(achievement.events())
        .allMatch(event -> AchievementUnlockedEvent.class.equals(event.event().getClass()));

    assertions.assertAll();
  }

  @Test
  public void progress()
  {
    final var achievement =
        AchievementAggregate.builder().identity("42").name(AchievementValueType.JOURNEY_BEGINS)
            .state(StateValueType.LOCKED)
            .build();

    achievement.progress(new ToDoCreatedEvent(LocalDateTime.now(),
        new pendenzenliste.domain.todos.IdentityValueObject("42")));

    final var assertions = new SoftAssertions();

    assertions.assertThat(achievement.aggregateRoot().identity())
        .isEqualTo(new IdentityValueObject("42"));
    assertions.assertThat(achievement.aggregateRoot().unlocked())
        .isNotNull();
    assertions.assertThat(achievement.aggregateRoot().name())
        .isEqualTo(AchievementValueType.JOURNEY_BEGINS);

    assertions.assertThat(achievement.events()).hasSize(1);
    assertions.assertThat(achievement.events())
        .allMatch(event -> AchievementUnlockedEvent.class.equals(event.event().getClass()));

    assertions.assertAll();
  }
}
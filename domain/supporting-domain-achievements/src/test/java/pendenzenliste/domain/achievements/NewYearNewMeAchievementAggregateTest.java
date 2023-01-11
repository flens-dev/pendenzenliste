package pendenzenliste.domain.achievements;

import java.time.LocalDateTime;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import pendenzenliste.domain.todos.IdentityValueObject;
import pendenzenliste.domain.todos.ToDoCreatedEvent;

class NewYearNewMeAchievementAggregateTest
{
  @Test
  public void trackProgress_eventIsNotOnNewYear()
  {
    final var achievement = NewYearNewMeAchievementAggregate.builder()
        .identity("42")
        .state(StateValueType.LOCKED)
        .build();

    final var createdEvent =
        new ToDoCreatedEvent(
            LocalDateTime.of(2023, 6, 1, 0, 0, 0),
            IdentityValueObject.of(42));

    achievement.trackProgress(createdEvent);

    final var assertions = new SoftAssertions();

    assertions.assertThat(achievement.aggregateRoot().identity())
        .isEqualTo(new pendenzenliste.domain.achievements.IdentityValueObject("42"));
    assertions.assertThat(achievement.aggregateRoot().unlocked()).isNull();
    assertions.assertThat(achievement.aggregateRoot().state()).isEqualTo(StateValueType.LOCKED);
    assertions.assertThat(achievement.progressItems())
        .containsExactly(
            new ProgressItemEntity(new pendenzenliste.domain.achievements.IdentityValueObject(
                "*"), 0, 1));
    assertions.assertThat(achievement.events()).isEmpty();

    assertions.assertAll();
  }

  @Test
  public void trackProgress_eventIsOnNewYear()
  {
    final var achievement = NewYearNewMeAchievementAggregate.builder()
        .identity("42")
        .state(StateValueType.LOCKED)
        .build();

    final var createdEvent =
        new ToDoCreatedEvent(
            LocalDateTime.of(2023, 1, 1, 0, 0, 0),
            IdentityValueObject.of(42));

    achievement.trackProgress(createdEvent);

    final var assertions = new SoftAssertions();

    assertions.assertThat(achievement.aggregateRoot().identity())
        .isEqualTo(new pendenzenliste.domain.achievements.IdentityValueObject("42"));
    assertions.assertThat(achievement.aggregateRoot().unlocked()).isNotNull();
    assertions.assertThat(achievement.aggregateRoot().state()).isEqualTo(StateValueType.UNLOCKED);
    assertions.assertThat(achievement.progressItems())
        .containsExactly(
            new ProgressItemEntity(new pendenzenliste.domain.achievements.IdentityValueObject(
                "*"), 1, 1));
    assertions.assertThat(achievement.events())
        .allMatch(event -> event.event() instanceof AchievementUnlockedEvent);

    assertions.assertAll();
  }
}
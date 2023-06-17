package pendenzenliste.achievements.model;

import java.time.LocalDateTime;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import pendenzenliste.todos.model.IdentityValueObject;
import pendenzenliste.todos.model.ToDoCreatedEvent;

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
        .isEqualTo(new pendenzenliste.achievements.model.IdentityValueObject("42"));
    assertions.assertThat(achievement.aggregateRoot().unlocked()).isNull();
    assertions.assertThat(achievement.aggregateRoot().state()).isEqualTo(StateValueType.LOCKED);
    assertions.assertThat(achievement.progressItems()).isEmpty();
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
        .isEqualTo(new pendenzenliste.achievements.model.IdentityValueObject("42"));
    assertions.assertThat(achievement.aggregateRoot().unlocked()).isNotNull();
    assertions.assertThat(achievement.aggregateRoot().state()).isEqualTo(StateValueType.UNLOCKED);
    assertions.assertThat(achievement.progressItems())
        .containsExactly(
            new ProgressItemEntity(new pendenzenliste.achievements.model.IdentityValueObject(
                "*"), 1, 1));
    assertions.assertThat(achievement.events())
        .allMatch(event -> event.event() instanceof AchievementUnlockedEvent);

    assertions.assertAll();
  }
}
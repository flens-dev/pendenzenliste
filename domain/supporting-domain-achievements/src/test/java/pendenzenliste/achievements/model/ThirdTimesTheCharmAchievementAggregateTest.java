package pendenzenliste.achievements.model;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import pendenzenliste.todos.model.IdentityValueObject;
import pendenzenliste.todos.model.ToDoReopenedEvent;

class ThirdTimesTheCharmAchievementAggregateTest
{
  @Test
  public void isCompleted_noProgress()
  {
    final var achievement = ThirdTimesTheCharmAchievementAggregate.builder()
        .identity("42")
        .state(StateValueType.LOCKED)
        .build();

    assertThat(achievement.isCompleted()).isFalse();
  }

  @Test
  public void isCompleted_reopenThreeDifferentToDos()
  {
    final var achievement = ThirdTimesTheCharmAchievementAggregate.builder()
        .identity("42")
        .state(StateValueType.LOCKED)
        .build();

    LocalDateTime currentTimestamp = LocalDateTime.of(2023, 1, 1, 0, 0);

    for (int i = 0; i < 3; i++)
    {
      final var event =
          new ToDoReopenedEvent(currentTimestamp,
              IdentityValueObject.of(i));

      achievement.trackProgress(event);

      currentTimestamp = currentTimestamp.plusHours(1);
    }

    assertThat(achievement.isCompleted()).isFalse();
  }

  @Test
  public void isCompleted_reopenTheSameToDoThreeTimes()
  {
    final var achievement = ThirdTimesTheCharmAchievementAggregate.builder()
        .identity("42")
        .state(StateValueType.LOCKED)
        .build();

    LocalDateTime currentTimestamp = LocalDateTime.of(2023, 1, 1, 0, 0);

    for (int i = 0; i < 3; i++)
    {
      final var event =
          new ToDoReopenedEvent(currentTimestamp,
              IdentityValueObject.of(42));

      achievement.trackProgress(event);

      currentTimestamp = currentTimestamp.plusHours(1);
    }

    assertThat(achievement.isCompleted()).isTrue();
  }
}
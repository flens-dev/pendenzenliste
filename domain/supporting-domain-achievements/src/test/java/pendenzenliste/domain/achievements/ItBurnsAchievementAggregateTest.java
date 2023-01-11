package pendenzenliste.domain.achievements;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import pendenzenliste.domain.todos.ToDoCompletedEvent;

class ItBurnsAchievementAggregateTest
{

  @Test
  public void isCompleted_noProgress()
  {
    final var achievement = ItBurnsAchievementAggregate.builder()
        .identity("42")
        .state(StateValueType.LOCKED)
        .build();

    assertThat(achievement.isCompleted()).isFalse();
  }

  @Test
  public void isCompleted_tenClosedToDosInDifferentWeeks()
  {
    final var achievement = ItBurnsAchievementAggregate.builder()
        .identity("42")
        .state(StateValueType.LOCKED)
        .build();

    LocalDateTime currentTimestamp = LocalDateTime.of(2023, 1, 1, 0, 0);

    for (int i = 0; i < 10; i++)
    {
      final var event =
          new ToDoCompletedEvent(currentTimestamp,
              new pendenzenliste.domain.todos.IdentityValueObject(String.valueOf(i)));

      achievement.trackProgress(event);

      currentTimestamp = currentTimestamp.plusWeeks(1);
    }

    assertThat(achievement.isCompleted()).isFalse();
  }

  @Test
  public void isCompleted_tenClosedToDosInTheSameWeek()
  {
    final var achievement = ItBurnsAchievementAggregate.builder()
        .identity("42")
        .state(StateValueType.LOCKED)
        .build();

    LocalDateTime currentTimestamp = LocalDateTime.of(2023, 1, 1, 0, 0);

    for (int i = 0; i < 10; i++)
    {
      final var event =
          new ToDoCompletedEvent(currentTimestamp,
              new pendenzenliste.domain.todos.IdentityValueObject(String.valueOf(i)));

      achievement.trackProgress(event);

      currentTimestamp = currentTimestamp.plusHours(1);
    }

    assertThat(achievement.isCompleted()).isTrue();
  }

  @Test
  public void isCompleted_closeTheSameToDoTenTimes()
  {
    final var achievement = ItBurnsAchievementAggregate.builder()
        .identity("42")
        .state(StateValueType.LOCKED)
        .build();

    LocalDateTime currentTimestamp =
        LocalDateTime.of(2023, 1, 1, 0, 0);

    for (int i = 0; i < 10; i++)
    {
      final var event =
          new ToDoCompletedEvent(currentTimestamp,
              pendenzenliste.domain.todos.IdentityValueObject.of(42));

      achievement.trackProgress(event);

      currentTimestamp = currentTimestamp.plusHours(1);
    }

    assertThat(achievement.isCompleted()).isFalse();
  }
}
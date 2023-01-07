package pendenzenliste.domain.achievements;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import pendenzenliste.domain.todos.IdentityValueObject;
import pendenzenliste.domain.todos.ToDoCompletedEvent;

class ItBurnsAchievementProgressTrackerTest
{

  @Test
  public void isCompleted_noProgress()
  {
    final var tracker = new ItBurnsAchievementProgressTracker();

    assertThat(tracker.isCompleted()).isFalse();
  }

  @Test
  public void isCompleted_tenClosedToDosInDifferentWeeks()
  {
    final var tracker = new ItBurnsAchievementProgressTracker();

    LocalDateTime currentTimestamp = LocalDateTime.of(2023, 1, 1, 0, 0);

    for (int i = 0; i < 10; i++)
    {
      final var event =
          new ToDoCompletedEvent(currentTimestamp, new IdentityValueObject(String.valueOf(i)));

      event.visit(tracker);

      currentTimestamp = currentTimestamp.plusWeeks(1);
    }

    assertThat(tracker.isCompleted()).isFalse();
  }

  @Test
  public void isCompleted_tenClosedToDosInTheSameWeek()
  {
    final var tracker = new ItBurnsAchievementProgressTracker();

    LocalDateTime currentTimestamp = LocalDateTime.of(2023, 1, 1, 0, 0);

    for (int i = 0; i < 10; i++)
    {
      final var event =
          new ToDoCompletedEvent(currentTimestamp, new IdentityValueObject(String.valueOf(i)));

      event.visit(tracker);

      currentTimestamp = currentTimestamp.plusHours(1);
    }

    assertThat(tracker.isCompleted()).isTrue();
  }

  @Test
  public void isCompleted_closeTheSameToDoTenTimes()
  {
    final var tracker = new ItBurnsAchievementProgressTracker();

    LocalDateTime currentTimestamp = LocalDateTime.of(2023, 1, 1, 0, 0);

    for (int i = 0; i < 10; i++)
    {
      final var event =
          new ToDoCompletedEvent(currentTimestamp, IdentityValueObject.of(42));

      event.visit(tracker);

      currentTimestamp = currentTimestamp.plusHours(1);
    }

    assertThat(tracker.isCompleted()).isFalse();
  }
}
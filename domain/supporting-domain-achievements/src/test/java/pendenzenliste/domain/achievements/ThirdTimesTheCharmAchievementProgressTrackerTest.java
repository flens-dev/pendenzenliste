package pendenzenliste.domain.achievements;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import pendenzenliste.domain.todos.IdentityValueObject;
import pendenzenliste.domain.todos.ToDoCompletedEvent;

class ThirdTimesTheCharmAchievementProgressTrackerTest
{

  @Test
  public void isCompleted_noProgress()
  {
    final var tracker = new ThirdTimesTheCharmAchievementProgressTracker();

    assertThat(tracker.isCompleted()).isFalse();
  }

  @Test
  public void isCompleted_reopenThreeDifferentToDos()
  {
    final var tracker = new ThirdTimesTheCharmAchievementProgressTracker();

    LocalDateTime currentTimestamp = LocalDateTime.of(2023, 1, 1, 0, 0);

    for (int i = 0; i < 3; i++)
    {
      final var event =
          new ToDoCompletedEvent(currentTimestamp, IdentityValueObject.of(i));

      event.visit(tracker);

      currentTimestamp = currentTimestamp.plusHours(1);
    }

    assertThat(tracker.isCompleted()).isFalse();
  }

  @Test
  public void isCompleted_reopenTheSameToDoThreeTimes()
  {
    final var tracker = new ThirdTimesTheCharmAchievementProgressTracker();

    LocalDateTime currentTimestamp = LocalDateTime.of(2023, 1, 1, 0, 0);

    for (int i = 0; i < 3; i++)
    {
      final var event =
          new ToDoCompletedEvent(currentTimestamp, IdentityValueObject.of(42));

      event.visit(tracker);

      currentTimestamp = currentTimestamp.plusHours(1);
    }

    assertThat(tracker.isCompleted()).isTrue();
  }
}
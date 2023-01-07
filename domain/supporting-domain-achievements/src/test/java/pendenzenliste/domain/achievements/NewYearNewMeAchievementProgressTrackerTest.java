package pendenzenliste.domain.achievements;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import pendenzenliste.domain.todos.IdentityValueObject;
import pendenzenliste.domain.todos.ToDoCreatedEvent;

class NewYearNewMeAchievementProgressTrackerTest
{

  @Test
  public void isCompleted_eventNotOnNewYear()
  {
    final var tracker = new NewYearNewMeAchievementProgressTracker();

    final var event =
        new ToDoCreatedEvent(
            LocalDateTime.of(2023, 6, 1, 0, 0, 0),
            IdentityValueObject.of(42));

    event.visit(tracker);

    assertThat(tracker.isCompleted()).isFalse();
  }

  @Test
  public void isCompleted_completedOnNewYear()
  {
    final var tracker = new NewYearNewMeAchievementProgressTracker();

    final var event =
        new ToDoCreatedEvent(
            LocalDateTime.of(2023, 1, 1, 0, 0, 0),
            IdentityValueObject.of(42));

    event.visit(tracker);

    assertThat(tracker.isCompleted()).isTrue();
  }
}
package pendenzenliste.domain.achievements;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import pendenzenliste.domain.todos.IdentityValueObject;
import pendenzenliste.domain.todos.ToDoCompletedEvent;

class DonezoAchievementProgressTrackerTest
{

  @Test
  public void achievement()
  {
    final var tracker = new DonezoAchievementProgressTracker();

    assertThat(tracker.achievement()).isEqualTo(AchievementValueType.DONEZO);
  }

  @Test
  public void isCompleted_incomplete()
  {
    final var tracker = new DonezoAchievementProgressTracker();

    assertThat(tracker.isCompleted()).isFalse();
  }

  @Test
  public void isCompleted_complete()
  {
    final var tracker = new DonezoAchievementProgressTracker();

    final var event =
        new ToDoCompletedEvent(LocalDateTime.now(), IdentityValueObject.random());

    event.visit(tracker);

    assertThat(tracker.isCompleted()).isTrue();
  }
}
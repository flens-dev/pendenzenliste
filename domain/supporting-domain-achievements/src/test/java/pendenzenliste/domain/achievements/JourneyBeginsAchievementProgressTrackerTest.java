package pendenzenliste.domain.achievements;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import pendenzenliste.domain.todos.IdentityValueObject;
import pendenzenliste.domain.todos.ToDoCreatedEvent;

class JourneyBeginsAchievementProgressTrackerTest
{

  @Test
  public void achievement()
  {
    final var tracker = new JourneyBeginsAchievementProgressTracker();

    assertThat(tracker.achievement()).isEqualTo(AchievementValueType.JOURNEY_BEGINS);
  }

  @Test
  public void isCompleted_incomplete()
  {
    final var tracker = new JourneyBeginsAchievementProgressTracker();

    assertThat(tracker.isCompleted()).isFalse();
  }

  @Test
  public void isCompleted_complete()
  {
    final var tracker = new JourneyBeginsAchievementProgressTracker();

    final var event =
        new ToDoCreatedEvent(LocalDateTime.now(), IdentityValueObject.random());

    event.visit(tracker);

    assertThat(tracker.isCompleted()).isTrue();
  }
}
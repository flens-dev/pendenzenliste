package pendenzenliste.domain.achievements;

import java.time.LocalDateTime;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import pendenzenliste.domain.todos.ToDoCompletedEvent;

class DonezoAchievementAggregateTest
{

  @Test
  public void trackProgress()
  {
    final var achievement =
        DonezoAchievementAggregate.builder().identity("42").state(StateValueType.LOCKED).build();

    achievement.trackProgress(new ToDoCompletedEvent(LocalDateTime.now(),
        new pendenzenliste.domain.todos.IdentityValueObject("42")));

    final var assertions = new SoftAssertions();

    assertions.assertThat(achievement.aggregateRoot().identity())
        .isEqualTo(new IdentityValueObject("42"));
    assertions.assertThat(achievement.aggregateRoot().unlocked()).isNotNull();
    assertions.assertThat(achievement.aggregateRoot().state()).isEqualTo(StateValueType.UNLOCKED);
    assertions.assertThat(achievement.progressItems()).containsExactly(
        new ProgressItemEntity(new IdentityValueObject("*"), 1, 1));
    assertions.assertThat(achievement.events()).hasSize(1);
    assertions.assertThat(achievement.events())
        .allMatch(event -> event.event() instanceof AchievementUnlockedEvent);

    assertions.assertAll();
  }
}
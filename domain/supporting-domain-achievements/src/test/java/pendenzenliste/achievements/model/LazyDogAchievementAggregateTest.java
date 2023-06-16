package pendenzenliste.achievements.model;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import pendenzenliste.todos.model.IdentityValueObject;
import pendenzenliste.todos.model.ToDoCreatedEvent;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

class LazyDogAchievementAggregateTest {

    @Test
    public void incomplete() {
        final var achievement =
                LazyDogAchievementAggregate.builder()
                        .identity("42")
                        .state(StateValueType.LOCKED)
                        .build();

        achievement.trackProgress(new ToDoCreatedEvent(LocalDateTime.now(),
                IdentityValueObject.of(42)));

        final var assertions = new SoftAssertions();

        assertions.assertThat(achievement.aggregateRoot().identity())
                .isEqualTo(new pendenzenliste.achievements.model.IdentityValueObject("42"));
        assertions.assertThat(achievement.aggregateRoot().unlocked()).isNull();
        assertions.assertThat(achievement.aggregateRoot().state()).isEqualTo(StateValueType.LOCKED);
        assertions.assertThat(achievement.progressItems()).containsExactly(
                ProgressItemEntity.onlyOnce("42"));
        assertions.assertThat(achievement.events()).isEmpty();

        assertions.assertAll();
    }

    @Test
    public void complete() {
        final var achievement =
                LazyDogAchievementAggregate.builder()
                        .identity("42")
                        .state(StateValueType.LOCKED)
                        .build();

        final var threeMonthsAgo = LocalDateTime.now().minus(3, ChronoUnit.MONTHS);

        achievement.trackProgress(new ToDoCreatedEvent(threeMonthsAgo,
                IdentityValueObject.of(42)));

        final var assertions = new SoftAssertions();

        assertions.assertThat(achievement.aggregateRoot().identity())
                .isEqualTo(new pendenzenliste.achievements.model.IdentityValueObject("42"));
        assertions.assertThat(achievement.aggregateRoot().unlocked()).isNotNull();
        assertions.assertThat(achievement.aggregateRoot().state()).isEqualTo(StateValueType.UNLOCKED);
        assertions.assertThat(achievement.progressItems()).containsExactly(
                ProgressItemEntity.onlyOnce("42"));
        assertions.assertThat(achievement.events()).hasSize(1);
        assertions.assertThat(achievement.events())
                .allMatch(event -> event.event() instanceof AchievementUnlockedEvent);

        assertions.assertAll();
    }
}
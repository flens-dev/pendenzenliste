package pendenzenliste.achievements.model;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import pendenzenliste.todos.model.ToDoCompletedEvent;
import pendenzenliste.todos.model.ToDoCreatedEvent;

import java.time.LocalDateTime;

class AllDoneAchievementAggregateTest {

    @Test
    public void incompleteProgress_oneTodo() {

        final var achievement = AllDoneAchievementAggregate.builder()
                .identity("42")
                .state(StateValueType.LOCKED)
                .build();

        achievement.trackProgress(new ToDoCreatedEvent(LocalDateTime.now(),
                new pendenzenliste.todos.model.IdentityValueObject("42")));

        final var assertions = new SoftAssertions();

        assertions.assertThat(achievement.aggregateRoot().identity())
                .isEqualTo(new IdentityValueObject("42"));
        assertions.assertThat(achievement.aggregateRoot().unlocked()).isNull();
        assertions.assertThat(achievement.aggregateRoot().state()).isEqualTo(StateValueType.LOCKED);
        assertions.assertThat(achievement.progressItems()).containsExactly(
                new ProgressItemEntity(new IdentityValueObject("42"), 0, 1));
        assertions.assertThat(achievement.events()).hasSize(0);
        assertions.assertThat(achievement.events()).isEmpty();

        assertions.assertAll();
    }

    @Test
    public void incompleteProgress_twoTodos() {
        final var achievement = AllDoneAchievementAggregate.builder()
                .identity("42")
                .state(StateValueType.LOCKED)
                .build();

        achievement.trackProgress(new ToDoCreatedEvent(LocalDateTime.now(),
                new pendenzenliste.todos.model.IdentityValueObject("42")));
        achievement.trackProgress(new ToDoCreatedEvent(LocalDateTime.now(),
                new pendenzenliste.todos.model.IdentityValueObject("43")));
        achievement.trackProgress(new ToDoCompletedEvent(LocalDateTime.now(),
                new pendenzenliste.todos.model.IdentityValueObject("42")));

        final var assertions = new SoftAssertions();

        assertions.assertThat(achievement.aggregateRoot().identity())
                .isEqualTo(new IdentityValueObject("42"));
        assertions.assertThat(achievement.aggregateRoot().unlocked()).isNull();
        assertions.assertThat(achievement.aggregateRoot().state()).isEqualTo(StateValueType.LOCKED);
        assertions.assertThat(achievement.progressItems()).containsExactly(
                new ProgressItemEntity(new IdentityValueObject("42"), 1, 1),
                new ProgressItemEntity(new IdentityValueObject("43"), 0, 1));
        assertions.assertThat(achievement.events()).isEmpty();

        assertions.assertAll();
    }

    @Test
    public void completeProgress_oneTodo() {
        final var achievement = AllDoneAchievementAggregate.builder()
                .identity("42")
                .state(StateValueType.LOCKED)
                .build();

        achievement.trackProgress(new ToDoCreatedEvent(LocalDateTime.now(),
                new pendenzenliste.todos.model.IdentityValueObject("42")));
        achievement.trackProgress(new ToDoCompletedEvent(LocalDateTime.now(),
                new pendenzenliste.todos.model.IdentityValueObject("42")));

        final var assertions = new SoftAssertions();

        assertions.assertThat(achievement.aggregateRoot().identity())
                .isEqualTo(new IdentityValueObject("42"));
        assertions.assertThat(achievement.aggregateRoot().unlocked()).isNotNull();
        assertions.assertThat(achievement.aggregateRoot().state()).isEqualTo(StateValueType.UNLOCKED);
        assertions.assertThat(achievement.progressItems()).containsExactly(
                new ProgressItemEntity(new IdentityValueObject("42"), 1, 1));
        assertions.assertThat(achievement.events()).hasSize(1);
        assertions.assertThat(achievement.events())
                .allMatch(event -> event.event() instanceof AchievementUnlockedEvent);

        assertions.assertAll();
    }

    @Test
    public void completedProgress_twoTodos() {
        final var achievement = AllDoneAchievementAggregate.builder()
                .identity("42")
                .state(StateValueType.LOCKED)
                .build();

        achievement.trackProgress(new ToDoCreatedEvent(LocalDateTime.now(),
                new pendenzenliste.todos.model.IdentityValueObject("42")));
        achievement.trackProgress(new ToDoCreatedEvent(LocalDateTime.now(),
                new pendenzenliste.todos.model.IdentityValueObject("43")));
        achievement.trackProgress(new ToDoCompletedEvent(LocalDateTime.now(),
                new pendenzenliste.todos.model.IdentityValueObject("42")));
        achievement.trackProgress(new ToDoCompletedEvent(LocalDateTime.now(),
                new pendenzenliste.todos.model.IdentityValueObject("43")));

        final var assertions = new SoftAssertions();

        assertions.assertThat(achievement.aggregateRoot().identity())
                .isEqualTo(new IdentityValueObject("42"));
        assertions.assertThat(achievement.aggregateRoot().unlocked()).isNotNull();
        assertions.assertThat(achievement.aggregateRoot().state()).isEqualTo(StateValueType.UNLOCKED);
        assertions.assertThat(achievement.progressItems()).containsExactly(
                new ProgressItemEntity(new IdentityValueObject("42"), 1, 1),
                new ProgressItemEntity(new IdentityValueObject("43"), 1, 1));
        assertions.assertThat(achievement.events()).hasSize(1);
        assertions.assertThat(achievement.events())
                .allMatch(event -> event.event() instanceof AchievementUnlockedEvent);

        assertions.assertAll();
    }
}
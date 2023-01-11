package pendenzenliste.domain.achievements;

import java.io.Serializable;
import java.util.Collection;

import pendenzenliste.domain.todos.ToDoEvent;

/**
 * The interface for objects that represent an achievement aggregate.
 */
public interface AchievementAggregate extends Serializable
{
  /**
   * The aggregate root entity.
   *
   * @return The aggregate root entity.
   */
  AchievementEntity aggregateRoot();

  /**
   * The event entities.
   *
   * @return The event entities.
   */
  Collection<AchievementEventEntity> events();

  /**
   * The progress entities.
   *
   * @return The progress entities.
   */
  Collection<ProgressItemEntity> progressItems();

  /**
   * Replaces the given old event with the new event.
   *
   * @param oldEvent The old event.
   * @param newEvent The new event.
   */
  void replaceEvent(AchievementEventEntity oldEvent, AchievementEventEntity newEvent);

  /**
   * Checks whether the achievement has been completed.
   *
   * @return True if the achievement has been completed, otherwise false.
   */
  default boolean isCompleted()
  {
    for (final ProgressItemEntity progress : progressItems())
    {
      if (progress.isCompleted())
      {
        return true;
      }
    }

    return false;
  }

  /**
   * Checks whether the achievement is currently locked.
   *
   * @return True if the achievement is locked.
   */
  default boolean isLocked()
  {
    return StateValueType.LOCKED.equals(aggregateRoot().state());
  }

  /**
   * Unlocks the achievement.
   */
  void unlockIfCompleted();

  /**
   * Tracks the progress based on the given event.
   *
   * @param event The event.
   */
  void trackProgress(final ToDoEvent event);

  /**
   * Tracks the progress based on the given event.
   *
   * @param event The event.
   */
  void trackProgress(final AchievementEvent event);
}

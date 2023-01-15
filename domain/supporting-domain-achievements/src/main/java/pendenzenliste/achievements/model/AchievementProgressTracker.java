package pendenzenliste.achievements.model;

import java.io.Serializable;

import pendenzenliste.todos.model.ToDoCompletedEvent;
import pendenzenliste.todos.model.ToDoCreatedEvent;
import pendenzenliste.todos.model.ToDoDeletedEvent;
import pendenzenliste.todos.model.ToDoEventVisitor;
import pendenzenliste.todos.model.ToDoReopenedEvent;
import pendenzenliste.todos.model.ToDoUpdatedEvent;

/**
 * The common interface for objects that track the progress of an achievement.
 * <p>
 * This interface implements default methods for the {@link ToDoEventVisitor} interface, that do
 * nothing.
 * This is useful for implementations that are only interested in specific events and reduces the
 * need for unnecessary boilerplate.
 */
public interface AchievementProgressTracker extends ToDoEventVisitor, Serializable
{

  /**
   * Checks whether the progress is completed.
   *
   * @return True if the progress is completed.
   */
  boolean isCompleted();

  /**
   * The name of the achievement.
   *
   * @return The name of the achievement.
   */
  AchievementValueType achievement();

  /**
   * {@inheritDoc}
   */
  @Override
  default void visit(final ToDoCompletedEvent event)
  {
  }

  /**
   * {@inheritDoc}
   */
  @Override
  default void visit(final ToDoCreatedEvent event)
  {
  }

  /**
   * {@inheritDoc}
   */
  @Override
  default void visit(final ToDoDeletedEvent event)
  {
  }

  /**
   * {@inheritDoc}
   */
  @Override
  default void visit(final ToDoReopenedEvent event)
  {
  }

  /**
   * {@inheritDoc}
   */
  @Override
  default void visit(final ToDoUpdatedEvent event)
  {
  }
}

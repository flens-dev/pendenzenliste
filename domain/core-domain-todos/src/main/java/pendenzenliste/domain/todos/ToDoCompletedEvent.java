package pendenzenliste.domain.todos;

import java.time.LocalDateTime;

/**
 * An event that can be used to indicate that a todo has been completed.
 *
 * @param timestamp The timestamp.
 * @param identity  The identity of the todo.
 */
public record ToDoCompletedEvent(LocalDateTime timestamp, IdentityValueObject identity)
    implements ToDoEvent
{
  /**
   * {@inheritDoc}
   */
  @Override
  public void visit(final ToDoEventVisitor visitor)
  {
    visitor.visit(this);
  }
}

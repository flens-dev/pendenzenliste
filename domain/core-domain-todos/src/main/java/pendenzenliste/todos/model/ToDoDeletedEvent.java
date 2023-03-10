package pendenzenliste.todos.model;

import java.time.LocalDateTime;

/**
 * An event that can be used to indicate that the todo has been deleted.
 *
 * @param timestamp The timestamp.
 * @param identity  The identity.
 */
public record ToDoDeletedEvent(LocalDateTime timestamp, IdentityValueObject identity)
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

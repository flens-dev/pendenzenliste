package pendenzenliste.todos.model;

import java.time.LocalDateTime;

/**
 * An event that can be used to indicate that a todo has been updated.
 *
 * @param timestamp The timestamp.
 * @param identity  The identity.
 */
public record ToDoUpdatedEvent(LocalDateTime timestamp, IdentityValueObject identity)
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

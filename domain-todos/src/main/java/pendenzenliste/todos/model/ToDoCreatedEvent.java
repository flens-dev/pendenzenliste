package pendenzenliste.todos.model;

import java.time.LocalDateTime;

/**
 * An event that can be used to represent that a todo has been created.
 *
 * @param timestamp The timestamp.
 * @param identity  The identity.
 */
public record ToDoCreatedEvent(LocalDateTime timestamp, IdentityValueObject identity)
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

package pendenzenliste.javafx;

import java.time.LocalDateTime;

/**
 * An event that can be used to indicate that resetting a todo has been requested.
 *
 * @param timestamp The timestamp.
 * @param identity  The identity.
 */
public record ResetRequestedEvent(LocalDateTime timestamp, String identity) implements ToDoEvent
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

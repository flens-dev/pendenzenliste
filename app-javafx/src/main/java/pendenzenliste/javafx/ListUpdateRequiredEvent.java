package pendenzenliste.javafx;

import java.time.LocalDateTime;

/**
 * An event that can be used to indicate that a list update has been requested.
 *
 * @param timestamp The timestamp.
 */
public record ListUpdateRequiredEvent(LocalDateTime timestamp) implements ToDoEvent
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

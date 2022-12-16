package pendenzenliste.javafx;

import java.time.LocalDateTime;

/**
 * An event that can be used to indicate that saving a todo has been requested.
 *
 * @param timestamp   The timestamp.
 * @param identity    The identity.
 * @param headline    The headline.
 * @param description The description.
 */
public record SaveRequestedEvent(LocalDateTime timestamp, String identity, String headline,
                                 String description) implements ToDoEvent
{
  @Override
  public void visit(final ToDoEventVisitor visitor)
  {
    visitor.visit(this);
  }
}

package pendenzenliste.javafx;

import java.time.LocalDateTime;

/**
 * An event that can be used to clear the editor.
 */
public record ClearEditorRequestedEvent(LocalDateTime timestamp) implements ToDoEvent
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

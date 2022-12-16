package pendenzenliste.javafx;

import java.time.LocalDateTime;

/**
 * A todo specific event.
 */
public interface ToDoEvent
{
  LocalDateTime timestamp();

  /**
   * Visits the given visitor.
   *
   * @param visitor The visitor.
   */
  void visit(final ToDoEventVisitor visitor);
}

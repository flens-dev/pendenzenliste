package pendenzenliste.javafx;

/**
 * A visitor that can be used to visit the various types of the todo events.
 */
public interface ToDoEventVisitor
{
  /**
   * Visits the given event.
   *
   * @param event The event.
   */
  void visit(ClearEditorRequestedEvent event);

  /**
   * Visits the given event.
   *
   * @param event The event.
   */
  void visit(DeleteRequestedEvent event);

  /**
   * Visits the given event.
   *
   * @param event The event.
   */
  void visit(CompleteRequestedEvent event);

  /**
   * Visits the given event.
   *
   * @param event The event.
   */
  void visit(ResetRequestedEvent event);

  /**
   * Visits the given event.
   *
   * @param event The event.
   */
  void visit(SaveRequestedEvent event);
}

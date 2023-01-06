package pendenzenliste.domain.todos;

/**
 * A visitor that can be used to visit the concrete types of the {@link ToDoEvent}.
 */
public interface ToDoEventVisitor
{
  /**
   * Visits the given event.
   *
   * @param event The event.
   */
  void visit(ToDoCompletedEvent event);

  /**
   * Visits the given event.
   *
   * @param event The event.
   */
  void visit(ToDoCreatedEvent event);

  /**
   * Visits the given event.
   *
   * @param event The event.
   */
  void visit(ToDoDeletedEvent event);

  /**
   * Visits the given event.
   *
   * @param event The event.
   */
  void visit(ToDoResetEvent event);

  /**
   * Visits the given event.
   *
   * @param event The event.
   */
  void visit(ToDoUpdatedEvent event);
}

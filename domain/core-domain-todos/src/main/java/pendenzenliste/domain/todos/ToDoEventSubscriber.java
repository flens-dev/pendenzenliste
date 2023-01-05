package pendenzenliste.domain.todos;

/**
 * The common interface for objects that act as a subscriber for todo specific events.
 */
public interface ToDoEventSubscriber
{
  /**
   * Handles the next
   *
   * @param event The event.
   */
  void next(final ToDoEvent event);
}

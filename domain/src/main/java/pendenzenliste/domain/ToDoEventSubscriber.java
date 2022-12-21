package pendenzenliste.domain;

/**
 * The common interface for objects that act as a subscriber for todo specific events.
 */
public interface ToDoEventSubscriber
{
  /**
   * Handles the next
   *
   * @param event
   */
  void next(final ToDoEvent event);
}

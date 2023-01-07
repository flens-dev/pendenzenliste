package pendenzenliste.messaging;

/**
 * The common interface for objects that subscribe to events.
 *
 * @param <T> The type of the event.
 */
public interface Subscriber<T>
{
  /**
   * Handles the given event.
   *
   * @param event The event.
   */
  void onEvent(T event);

  /**
   * The event type.
   *
   * @return The event type.
   */
  Class<T> eventType();
}

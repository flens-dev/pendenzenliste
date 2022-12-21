package pendenzenliste.domain;

import java.util.ServiceLoader;

/**
 * A publisher that can be used to
 */
public interface ToDoEventPublisher
{
  /**
   * Publishes an event to the subscribers.
   *
   * @param event The event that should be published.
   */
  void publish(ToDoEvent event);

  /**
   * Retrieves the default event publisher.
   *
   * @return The default event publisher.
   */
  static ToDoEventPublisher defaultPublisher()
  {
    return ServiceLoader.load(ToDoEventPublisher.class).findFirst()
        .orElseThrow(() -> new IllegalStateException("Failed to load default event publisher"));
  }
}

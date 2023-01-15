package pendenzenliste.messaging;

import java.util.ServiceLoader;

/**
 * The interface for objects that represent an event bus.
 */
public interface EventBus
{
  /**
   * Publishes the given event to the bus.
   *
   * @param event The event.
   */
  void publish(Object event);

  /**
   * Subscribes the given subscriber to the event bus.
   *
   * @param subscriber The subscriber.
   * @param <T>        The type of the subscribed event.
   */
  <T> void subscribe(Subscriber<T> subscriber);

  /**
   * Unsubscribes the given subscriber from the event bus.
   *
   * @param subscriber The subscriber.
   * @param <T>        The type of the subscribed event.
   */
  <T> void unsubscribe(Subscriber<T> subscriber);

  /**
   * Retrieves the default event bus.
   *
   * @return The default event bus.
   */
  static EventBus defaultEventBus()
  {
    return ServiceLoader.load(EventBus.class).findFirst()
        .orElseThrow(() -> new IllegalStateException("No default implementation found"));
  }
}

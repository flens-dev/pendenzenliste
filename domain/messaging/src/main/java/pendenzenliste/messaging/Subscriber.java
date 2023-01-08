package pendenzenliste.messaging;

import java.util.Collection;
import java.util.ServiceLoader;
import java.util.stream.Stream;

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
   * The supported event types.
   *
   * @return The supported event types.
   */
  Collection<Class<? extends T>> eventTypes();

  /**
   * Loads the subscriber services.
   *
   * @return The subscriber services.
   */
  static Stream<Subscriber<?>> loadSubscriberServices()
  {
    return ServiceLoader.load(Subscriber.class).stream()
        .map(provider -> (Subscriber<?>) provider.get());
  }
}

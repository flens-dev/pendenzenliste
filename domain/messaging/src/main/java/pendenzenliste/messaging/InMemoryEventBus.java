package pendenzenliste.messaging;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * An in-memory implementation of the {@link EventBus}.
 */
public final class InMemoryEventBus implements EventBus
{
  private static final ExecutorService THREAD_POOL = Executors.newFixedThreadPool(10);

  private static final Collection<Subscriber<?>> SUBSCRIBERS = new ArrayList<>();


  /**
   * {@inheritDoc}
   */
  @Override
  public void publish(final Object event)
  {
    for (final Subscriber<?> subscriber : SUBSCRIBERS)
    {
      if (subscriber.eventType().isAssignableFrom(event.getClass()))
      {
        THREAD_POOL.submit(() -> publishEvent(subscriber, event));
      }
    }
  }

  /**
   * Publishes the given event to the subscriber.
   *
   * @param subscriber The subscriber.
   * @param event      The event.
   * @param <T>        The event type.
   */
  private <T> void publishEvent(final Subscriber<T> subscriber, final Object event)
  {
    subscriber.onEvent(cast(subscriber.eventType(), event));
  }

  /**
   * Casts the event to the given type.
   *
   * @param type  The type.
   * @param event The event.
   * @param <T>   The type.
   * @return The event.
   */
  private <T> T cast(final Class<T> type, Object event)
  {
    return (T) event;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T> void subscribe(final Subscriber<T> subscriber)
  {
    SUBSCRIBERS.add(subscriber);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T> void unsubscribe(final Subscriber<T> subscriber)
  {
    SUBSCRIBERS.remove(subscriber);
  }
}

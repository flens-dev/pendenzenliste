package pendenzenliste.messaging.inmemory;

import pendenzenliste.messaging.EventBus;
import pendenzenliste.messaging.Subscriber;

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

  static
  {
    Subscriber.loadSubscriberServices().forEach(SUBSCRIBERS::add);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void publish(final Object event)
  {
    for (final Subscriber<?> subscriber : SUBSCRIBERS)
    {
      if (subscriberIsInterestedInEvent(subscriber, event))
      {
        THREAD_POOL.submit(() -> publishEvent(subscriber, event));
      }
    }
  }

  /**
   * Checks whether the given subscriber is interested in the given type of event.
   *
   * @param event      The event.
   * @param subscriber The subscriber.
   * @return True if the subscriber is interested in the given event.
   */
  private boolean subscriberIsInterestedInEvent(final Subscriber<?> subscriber,
                                                final Object event)
  {
    for (final Class<?> eventType : subscriber.eventTypes())
    {
      if (eventType.isAssignableFrom(event.getClass()))
      {
        return true;
      }
    }

    return false;
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
    subscriber.onEvent((T) event);
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

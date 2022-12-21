package pendenzenliste.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A publisher that uses a thread pool to handle multiple concurrent subscribers.
 */
public class MultiThreadedToDoEventPublisher
    implements ToDoEventPublisher, ToDoEventSubscriptionTopic
{
  private static final ExecutorService THREAD_POOL = Executors.newFixedThreadPool(5);

  private static final Collection<ToDoEventSubscriber> SUBSCRIBERS = new ArrayList<>();

  /**
   * {@inheritDoc}
   */
  @Override
  public void publish(final ToDoEvent event)
  {
    for (final ToDoEventSubscriber subscriber : SUBSCRIBERS)
    {
      THREAD_POOL.submit(() -> subscriber.next(event));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void subscribe(final ToDoEventSubscriber subscriber)
  {
    SUBSCRIBERS.add(subscriber);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void unsubscribe(final ToDoEventSubscriber subscriber)
  {
    SUBSCRIBERS.remove(subscriber);
  }
}

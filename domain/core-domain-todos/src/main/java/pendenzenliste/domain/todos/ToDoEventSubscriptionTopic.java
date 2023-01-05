package pendenzenliste.domain.todos;

import java.util.ServiceLoader;

/**
 * The common interface for objects that represent a specific todo event subscription topic.
 */
public interface ToDoEventSubscriptionTopic
{
  /**
   * Subscribes the given subscriber to the topic.
   *
   * @param subscriber The subscriber.
   */
  void subscribe(ToDoEventSubscriber subscriber);

  /**
   * Unsubscribes the given subscriber from the topic.
   *
   * @param subscriber The subscriber.
   */
  void unsubscribe(ToDoEventSubscriber subscriber);

  /**
   * Retrieves the default subscription topic.
   *
   * @return The default subscription topic.
   */
  static ToDoEventSubscriptionTopic defaultSubscriptionTopic()
  {
    return ServiceLoader.load(ToDoEventSubscriptionTopic.class).findFirst()
        .orElseThrow(() -> new IllegalStateException("Failed to load default subscription topic"));
  }
}

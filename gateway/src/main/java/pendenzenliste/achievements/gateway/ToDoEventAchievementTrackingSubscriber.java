package pendenzenliste.achievements.gateway;

import java.util.Collection;
import java.util.List;

import static java.util.Objects.requireNonNull;

import pendenzenliste.achievements.model.StateValueType;
import pendenzenliste.messaging.Subscriber;
import pendenzenliste.todos.model.ToDoEvent;

/**
 * A subscriber that subscribes to todo events in order to track the achievements.
 */
public class ToDoEventAchievementTrackingSubscriber implements Subscriber<ToDoEvent>
{
  private final AchievementGateway gateway;

  /**
   * Creates a new instance.
   */
  public ToDoEventAchievementTrackingSubscriber()
  {
    this(AchievementGatewayProvider.defaultProvider().getInstance());
  }

  /**
   * Creates a new instance.
   *
   * @param gateway The gateway.
   */
  public ToDoEventAchievementTrackingSubscriber(final AchievementGateway gateway)
  {
    this.gateway = requireNonNull(gateway, "The gateway may not be null");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onEvent(final ToDoEvent event)
  {
    gateway.fetchAll()
        .filter(achievement -> StateValueType.LOCKED.equals(achievement.aggregateRoot().state()))
        .forEach(achievement -> {
          achievement.trackProgress(event);
          gateway.store(achievement);
        });
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<Class<? extends ToDoEvent>> eventTypes()
  {
    return List.of(ToDoEvent.class);
  }
}

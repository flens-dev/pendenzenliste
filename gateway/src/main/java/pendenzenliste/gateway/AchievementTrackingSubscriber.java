package pendenzenliste.gateway;

import java.util.Collection;
import java.util.List;

import static java.util.Objects.requireNonNull;

import pendenzenliste.domain.achievements.StateValueType;
import pendenzenliste.domain.todos.ToDoEvent;
import pendenzenliste.messaging.Subscriber;

/**
 * A subscriber that subscribes to todo events in order to track the achievements.
 */
public class AchievementTrackingSubscriber implements Subscriber<ToDoEvent>
{
  private final AchievementGateway gateway;

  /**
   * Creates a new instance.
   */
  public AchievementTrackingSubscriber()
  {
    this(AchievementGatewayProvider.defaultProvider().getInstance());
  }

  /**
   * Creates a new instance.
   *
   * @param gateway The gateway.
   */
  public AchievementTrackingSubscriber(final AchievementGateway gateway)
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
        .filter(aggregate ->
            StateValueType.LOCKED.equals(aggregate.aggregateRoot().state()))
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

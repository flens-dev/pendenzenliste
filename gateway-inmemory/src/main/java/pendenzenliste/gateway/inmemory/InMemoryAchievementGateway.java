package pendenzenliste.gateway.inmemory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

import pendenzenliste.domain.achievements.AchievementAggregate;
import pendenzenliste.domain.achievements.AchievementEvent;
import pendenzenliste.domain.achievements.AchievementEventEntity;
import pendenzenliste.domain.achievements.IdentityValueObject;
import pendenzenliste.gateway.AchievementGateway;
import pendenzenliste.messaging.EventBus;

/**
 * An in-memory implementation of the {@link AchievementGateway}.
 */
public class InMemoryAchievementGateway implements AchievementGateway
{
  private static final Map<IdentityValueObject, AchievementAggregate> STORE =
      new ConcurrentHashMap<>();
  private final EventBus eventBus;

  /**
   * Creates a new instance.
   *
   * @param eventBus The event bus.
   */
  public InMemoryAchievementGateway(final EventBus eventBus)
  {
    this.eventBus = requireNonNull(eventBus, "The event bus may not be null");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<AchievementAggregate> findById(final IdentityValueObject identity)
  {
    return Optional.ofNullable(STORE.getOrDefault(identity, null));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Stream<AchievementAggregate> fetchAll()
  {
    return STORE.values().stream();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void store(final AchievementAggregate achievement)
  {
    final Collection<AchievementEventEntity> events = new ArrayList<>();
    final Collection<AchievementEvent> unpublishedEvents = new ArrayList<>();

    for (final AchievementEventEntity event : achievement.events())
    {
      if (event.identity() == null)
      {
        final var unpublishedEvent = event.withIdentity(IdentityValueObject.random());

        events.add(unpublishedEvent);
        unpublishedEvents.add(unpublishedEvent.event());
      } else
      {
        events.add(event);
      }
    }

    achievement.updateEvents(events);
    STORE.put(achievement.aggregateRoot().identity(), achievement);

    for (final AchievementEvent unpublishedEvent : unpublishedEvents)
    {
      eventBus.publish(unpublishedEvent);
    }
  }
}

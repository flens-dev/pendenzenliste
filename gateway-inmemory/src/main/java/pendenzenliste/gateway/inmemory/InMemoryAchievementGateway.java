package pendenzenliste.gateway.inmemory;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

import pendenzenliste.achievements.model.AchievementAggregate;
import pendenzenliste.achievements.model.AchievementEventEntity;
import pendenzenliste.achievements.model.IdentityValueObject;
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
    for (final AchievementEventEntity event :
        achievement.events().stream()
            .filter(event -> event.identity() == null)
            .toList())
    {
      final AchievementEventEntity unpublishedEvent =
          event.withIdentity(IdentityValueObject.random());

      achievement.replaceEvent(event, unpublishedEvent);
      eventBus.publish(unpublishedEvent.event());
    }

    STORE.put(achievement.aggregateRoot().identity(), achievement);
  }
}

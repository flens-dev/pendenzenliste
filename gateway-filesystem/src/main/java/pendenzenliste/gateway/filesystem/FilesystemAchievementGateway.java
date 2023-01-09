package pendenzenliste.gateway.filesystem;

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
 * An implementation of the {@link AchievementGateway} that can be used to store the achievements on
 * the filesystem.
 */
public class FilesystemAchievementGateway implements AchievementGateway
{
  private final FileStorage<Map<IdentityValueObject, AchievementAggregate>> storage;
  private final EventBus eventBus;


  /**
   * Creates a new instance.
   *
   * @param path     The path.
   * @param eventBus The event bus.
   */
  public FilesystemAchievementGateway(final String path, final EventBus eventBus)
  {
    this.storage = new FileStorage<>(requireNonNull(path, "The path may not be null"));
    this.eventBus = requireNonNull(eventBus, "The event bus may not be null");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<AchievementAggregate> findById(final IdentityValueObject identity)
  {
    return Optional.ofNullable(
        storage.getOr(new ConcurrentHashMap<>()).getOrDefault(identity, null));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Stream<AchievementAggregate> fetchAll()
  {
    return storage.getOr(new ConcurrentHashMap<>()).values().stream();
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
    final Map<IdentityValueObject, AchievementAggregate> cache =
        storage.getOr(new ConcurrentHashMap<>());

    cache.put(achievement.aggregateRoot().identity(), achievement);
    storage.flushToDisk(cache);

    for (final AchievementEvent unpublishedEvent : unpublishedEvents)
    {
      eventBus.publish(unpublishedEvent);
    }
  }
}

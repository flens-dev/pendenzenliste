package pendenzenliste.achievements.gateway.filesystem;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

import pendenzenliste.achievements.gateway.AchievementGateway;
import pendenzenliste.achievements.model.AchievementAggregate;
import pendenzenliste.achievements.model.AchievementEventEntity;
import pendenzenliste.achievements.model.IdentityValueObject;
import pendenzenliste.filesystem.util.FileStorage;
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
    for (final AchievementEventEntity event :
        achievement.events()
            .stream()
            .filter(event -> event.identity() == null)
            .toList())
    {
      final var unpublishedEvent =
          event.withIdentity(IdentityValueObject.random());

      achievement.replaceEvent(event, unpublishedEvent);
      eventBus.publish(unpublishedEvent.event());
    }

    final Map<IdentityValueObject, AchievementAggregate> cache =
        storage.getOr(new ConcurrentHashMap<>());

    cache.put(achievement.aggregateRoot().identity(), achievement);
    storage.flushToDisk(cache);
  }
}

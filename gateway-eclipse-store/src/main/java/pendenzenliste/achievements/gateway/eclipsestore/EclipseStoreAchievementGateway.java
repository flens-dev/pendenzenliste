package pendenzenliste.achievements.gateway.eclipsestore;

import org.eclipse.store.storage.embedded.types.EmbeddedStorageManager;
import pendenzenliste.achievements.gateway.AchievementGateway;
import pendenzenliste.achievements.model.*;
import pendenzenliste.messaging.EventBus;

import java.util.*;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

/**
 * An implementation of the {@link AchievementGateway} that makes use of the eclipse
 * store library to provide persistence.
 */
public class EclipseStoreAchievementGateway implements AchievementGateway {

    private final EmbeddedStorageManager storageManager;
    private final EventBus eventBus;

    /**
     * Creates a new instance.
     *
     * @param storageManager The storage manager.
     * @param eventBus       The event bus.
     */
    public EclipseStoreAchievementGateway(final EmbeddedStorageManager storageManager,
                                          final EventBus eventBus) {
        this.storageManager = requireNonNull(storageManager, "The storage manager may not be null");
        this.eventBus = requireNonNull(eventBus, "The event bus may not be null");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<AchievementAggregate> findById(final IdentityValueObject identity) {
        return getStore().getAchievements()
                .stream()
                .filter(achievement ->
                        Objects.equals(achievement.aggregateRoot().identity(), identity))
                .findFirst();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stream<AchievementAggregate> fetchAll() {
        return getStore().getAchievements().stream();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stream<AchievementAggregate> fetchLockedAchievements() {
        return fetchAll().filter(achievement -> StateValueType.LOCKED.equals(achievement.aggregateRoot().state()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void store(final AchievementAggregate achievement) {
        final Collection<AchievementEvent> unpublishedEvents =
                new ArrayList<>();

        for (final AchievementEventEntity event :
                achievement.events()
                        .stream()
                        .filter(event -> event.identity() == null)
                        .toList()) {
            final var unpublishedEvent =
                    event.withIdentity(IdentityValueObject.random());

            achievement.replaceEvent(event, unpublishedEvent);
            unpublishedEvents.add(unpublishedEvent.event());
        }

        final AchievementsStore store = getStore();

        final List<AchievementAggregate> achievements =
                store.getAchievements();

        if (achievements.contains(achievement)) {
            final int index = achievements.indexOf(achievement);
            achievements.set(index, achievement);
        } else {
            achievements.add(achievement);
        }

        store.setAchievements(achievements);
        storageManager.setRoot(store);
        storageManager.storeRoot();

        unpublishedEvents.forEach(eventBus::publish);
    }

    /**
     * Retrieves the store.
     *
     * @return The store.
     */
    private AchievementsStore getStore() {
        return Optional.ofNullable(storageManager.root())
                .map(r -> (AchievementsStore) r)
                .orElse(new AchievementsStore());
    }
}

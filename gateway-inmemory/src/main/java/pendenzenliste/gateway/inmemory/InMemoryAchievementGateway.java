package pendenzenliste.gateway.inmemory;

import pendenzenliste.achievements.gateway.AchievementGateway;
import pendenzenliste.achievements.model.*;
import pendenzenliste.messaging.EventBus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

/**
 * An in-memory implementation of the {@link AchievementGateway}.
 */
public class InMemoryAchievementGateway implements AchievementGateway {
    private static final Map<IdentityValueObject, AchievementAggregate> STORE =
            new ConcurrentHashMap<>();
    private final EventBus eventBus;

    /**
     * Creates a new instance.
     *
     * @param eventBus The event bus.
     */
    public InMemoryAchievementGateway(final EventBus eventBus) {
        this.eventBus = requireNonNull(eventBus, "The event bus may not be null");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<AchievementAggregate> findById(final IdentityValueObject identity) {
        return Optional.ofNullable(STORE.getOrDefault(identity, null));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stream<AchievementAggregate> fetchAll() {
        return STORE.values().stream();
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
        final Collection<AchievementEvent> unpublishedEvents = new ArrayList<>();

        for (final AchievementEventEntity event :
                achievement.events().stream()
                        .filter(event -> event.identity() == null)
                        .toList()) {
            final AchievementEventEntity unpublishedEvent =
                    event.withIdentity(IdentityValueObject.random());

            achievement.replaceEvent(event, unpublishedEvent);

            unpublishedEvents.add(unpublishedEvent.event());
        }

        STORE.put(achievement.aggregateRoot().identity(), achievement);

        unpublishedEvents.forEach(eventBus::publish);
    }
}

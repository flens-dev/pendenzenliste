package pendenzenliste.gateway.redis;

import pendenzenliste.achievements.gateway.AchievementGateway;
import pendenzenliste.achievements.model.*;
import pendenzenliste.messaging.EventBus;
import redis.clients.jedis.Jedis;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;
import static pendenzenliste.serialization.SerializationUtils.*;

/**
 * A redis based implementation of the {@link AchievementGateway}.
 */
public class RedisAchievementGateway implements AchievementGateway {
    private static final String LAST_MODIFIED_KEY = "last_modified";

    private static final String ACHIEVEMENTS_KEY = "achievements";

    private LocalDateTime lastModified = null;

    private final Map<IdentityValueObject, AchievementAggregate>
            cache = new ConcurrentHashMap<>();

    private final Jedis connection;
    private final EventBus eventBus;

    /**
     * Creates a new instance.
     *
     * @param connection The connection that should be used by this instance.
     * @param eventBus   The event bus that should be used by this instance.
     */
    public RedisAchievementGateway(final Jedis connection,
                                   final EventBus eventBus) {
        this.connection = requireNonNull(connection, "The connection may not be null");
        this.eventBus = requireNonNull(eventBus, "The event bus may not be null");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<AchievementAggregate> findById(final IdentityValueObject identity) {
        loadIfNecessary();

        return Optional.ofNullable(cache.getOrDefault(identity, null));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stream<AchievementAggregate> fetchAll() {
        loadIfNecessary();

        return cache.values().stream();
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
        loadIfNecessary();

        final Collection<AchievementEvent> eventQueue = new ArrayList<>();

        for (final AchievementEventEntity event :
                achievement.events()
                        .stream()
                        .filter(event -> event.identity() == null)
                        .toList()) {
            achievement.replaceEvent(event, event.withIdentity(IdentityValueObject.random()));
            eventQueue.add(event.event());
        }

        cache.put(achievement.aggregateRoot().identity(), achievement);

        flush();

        eventQueue.forEach(eventBus::publish);
    }

    /**
     * Flushes the data.
     */
    private void flush() {
        final var transaction = connection.multi();

        transaction.set(ACHIEVEMENTS_KEY.getBytes(StandardCharsets.UTF_8), serializeObject(cache));
        transaction.set(LAST_MODIFIED_KEY.getBytes(StandardCharsets.UTF_8),
                serializeObject(lastModified));

        transaction.exec();
    }

    /**
     * Loads the data from the storage if necessary.
     */
    private void loadIfNecessary() {
        final var storedTimestamp =
                deserializeObject(connection.get(LAST_MODIFIED_KEY.getBytes(StandardCharsets.UTF_8)),
                        LocalDateTime.class);

        final var hasBeenModified =
                lastModified == null || !Objects.equals(storedTimestamp, lastModified);

        if (hasBeenModified) {
            final var deserializedMap =
                    deserializeMap(connection.get(ACHIEVEMENTS_KEY.getBytes(StandardCharsets.UTF_8)),
                            IdentityValueObject.class, AchievementAggregate.class);

            if (deserializedMap != null) {
                cache.clear();
                cache.putAll(deserializedMap);
                lastModified = LocalDateTime.now();
            }
        }
    }
}

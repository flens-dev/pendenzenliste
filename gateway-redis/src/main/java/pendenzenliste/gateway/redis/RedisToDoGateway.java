package pendenzenliste.gateway.redis;

import pendenzenliste.messaging.EventBus;
import pendenzenliste.todos.gateway.ToDoGateway;
import pendenzenliste.todos.model.IdentityValueObject;
import pendenzenliste.todos.model.ToDoAggregate;
import pendenzenliste.todos.model.ToDoDeletedEvent;
import pendenzenliste.todos.model.ToDoEntity;
import redis.clients.jedis.Jedis;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;
import static pendenzenliste.serialization.SerializationUtils.*;

/**
 * An implementation of the {@link ToDoGateway} that makes use of a redis instance to store todos.
 */
public class RedisToDoGateway implements ToDoGateway {
    private static final String LAST_MODIFIED_KEY = "last_modified";

    private static final String TODO_KEY = "todos";

    private LocalDateTime lastModified = null;

    private final Map<IdentityValueObject, ToDoEntity> cache = new ConcurrentHashMap<>();

    private final Jedis connection;
    private final EventBus eventBus;

    /**
     * Creates a new instance.
     *
     * @param connection The connection that should be used by this instance.
     */
    public RedisToDoGateway(final Jedis connection,
                            final EventBus eventBus) {
        this.connection = requireNonNull(connection, "The connection may not be null");
        this.eventBus = requireNonNull(eventBus, "The event bus may not be null");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ToDoAggregate> findById(final IdentityValueObject id) {
        loadIfNecessary();

        return Optional.ofNullable(cache.getOrDefault(id, null))
                .map(todo -> new ToDoAggregate(todo, this, eventBus));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(final IdentityValueObject id) {
        loadIfNecessary();

        final var removed = cache.remove(id);
        final var hasBeenRemoved = removed != null;

        if (hasBeenRemoved) {
            flush();
            eventBus.publish(new ToDoDeletedEvent(LocalDateTime.now(), id));
        }

        return hasBeenRemoved;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void store(final ToDoAggregate todo) {
        loadIfNecessary();

        cache.put(todo.aggregateRoot().identity(), todo.aggregateRoot());

        flush();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stream<ToDoAggregate> fetchAll() {
        loadIfNecessary();

        return cache.values()
                .stream()
                .map(todo -> new ToDoAggregate(todo, this, eventBus));
    }

    /**
     * Flushes the data.
     */
    private void flush() {
        final var transaction = connection.multi();

        transaction.set(TODO_KEY.getBytes(StandardCharsets.UTF_8), serializeObject(cache));
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
                    deserializeMap(connection.get(TODO_KEY.getBytes(StandardCharsets.UTF_8)),
                            IdentityValueObject.class, ToDoEntity.class);

            if (deserializedMap != null) {
                cache.clear();
                cache.putAll(deserializedMap);
                lastModified = LocalDateTime.now();
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stream<ToDoAggregate> fetchAllCompletedBefore(final LocalDateTime timestamp) {
        return fetchAll().filter(todo -> todo.aggregateRoot().isClosed())
                .filter(todo -> todo.aggregateRoot().completed().isBefore(timestamp));
    }
}

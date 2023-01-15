package pendenzenliste.gateway.redis;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;
import static pendenzenliste.serialization.SerializationUtils.deserializeMap;
import static pendenzenliste.serialization.SerializationUtils.deserializeObject;
import static pendenzenliste.serialization.SerializationUtils.serializeObject;

import pendenzenliste.gateway.ToDoGateway;
import pendenzenliste.messaging.EventBus;
import pendenzenliste.todos.model.IdentityValueObject;
import pendenzenliste.todos.model.ToDoAggregate;
import pendenzenliste.todos.model.ToDoEvent;
import pendenzenliste.todos.model.ToDoEventEntity;
import redis.clients.jedis.Jedis;

/**
 * An implementation of the {@link ToDoGateway} that makes use of a redis instance to store todos.
 */
public class RedisToDoGateway implements ToDoGateway
{
  private static final String LAST_MODIFIED_KEY = "last_modified";

  private static final String TODO_KEY = "todos";

  private LocalDateTime lastModified = null;

  private final Map<IdentityValueObject, ToDoAggregate> cache = new ConcurrentHashMap<>();

  private final Jedis connection;
  private final EventBus eventBus;

  /**
   * Creates a new instance.
   *
   * @param connection The connection that should be used by this instance.
   */
  public RedisToDoGateway(final Jedis connection,
                          final EventBus eventBus)
  {
    this.connection = requireNonNull(connection, "The connection may not be null");
    this.eventBus = requireNonNull(eventBus, "The event bus may not be null");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<ToDoAggregate> findById(final IdentityValueObject id)
  {
    loadIfNecessary();

    return Optional.ofNullable(cache.getOrDefault(id, null));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean delete(final IdentityValueObject id)
  {
    loadIfNecessary();

    final var removed = cache.remove(id);
    final var hasBeenRemoved = removed != null;

    if (hasBeenRemoved)
    {
      flush();
    }

    return hasBeenRemoved;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void store(final ToDoAggregate todo)
  {
    loadIfNecessary();

    final Collection<ToDoEvent> eventQueue = new ArrayList<>();

    for (final ToDoEventEntity event :
        todo.events()
            .stream()
            .filter(event -> event.identity() == null)
            .toList())
    {
      todo.replaceEvent(event, event.withIdentity(IdentityValueObject.random()));
      eventQueue.add(event.event());
    }

    cache.put(todo.aggregateRoot().identity(), todo);

    flush();

    eventQueue.forEach(eventBus::publish);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Stream<ToDoAggregate> fetchAll()
  {
    loadIfNecessary();

    return cache.values().stream();
  }

  /**
   * Flushes the data.
   */
  private void flush()
  {
    final var transaction = connection.multi();

    transaction.set(TODO_KEY.getBytes(StandardCharsets.UTF_8), serializeObject(cache));
    transaction.set(LAST_MODIFIED_KEY.getBytes(StandardCharsets.UTF_8),
        serializeObject(lastModified));

    transaction.exec();
  }

  /**
   * Loads the data from the storage if necessary.
   */
  private void loadIfNecessary()
  {
    final var storedTimestamp =
        deserializeObject(connection.get(LAST_MODIFIED_KEY.getBytes(StandardCharsets.UTF_8)),
            LocalDateTime.class);

    final var hasBeenModified =
        lastModified == null || !Objects.equals(storedTimestamp, lastModified);

    if (hasBeenModified)
    {
      final var deserializedMap =
          deserializeMap(connection.get(TODO_KEY.getBytes(StandardCharsets.UTF_8)),
              IdentityValueObject.class, ToDoAggregate.class);

      if (deserializedMap != null)
      {
        cache.clear();
        cache.putAll(deserializedMap);
        lastModified = LocalDateTime.now();
      }
    }
  }
}

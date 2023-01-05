package pendenzenliste.gateway.redis;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;
import static pendenzenliste.gateway.redis.SerializationUtils.deserializeMap;
import static pendenzenliste.gateway.redis.SerializationUtils.deserializeObject;
import static pendenzenliste.gateway.redis.SerializationUtils.serializeObject;

import pendenzenliste.domain.todos.IdentityValueObject;
import pendenzenliste.domain.todos.ToDoEntity;
import pendenzenliste.gateway.ToDoGateway;
import redis.clients.jedis.Jedis;

/**
 * An implementation of the {@link ToDoGateway} that makes use of a redis instance to store todos.
 */
public class RedisToDoGateway implements ToDoGateway
{
  private static final String LAST_MODIFIED_KEY = "last_modified";

  private static final String TODO_KEY = "todos";

  private LocalDateTime lastModified = null;

  private final Map<IdentityValueObject, ToDoEntity> cache = new ConcurrentHashMap<>();

  private final Jedis connection;

  /**
   * Creates a new instance.
   *
   * @param connection The connection that should be used by this instance.
   */
  public RedisToDoGateway(final Jedis connection)
  {
    this.connection = requireNonNull(connection, "The connection may not be null");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<ToDoEntity> findById(final IdentityValueObject id)
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
  public void store(final ToDoEntity todo)
  {
    loadIfNecessary();

    cache.put(todo.identity(), todo);

    flush();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Stream<ToDoEntity> fetchAll()
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
              IdentityValueObject.class, ToDoEntity.class);

      if (deserializedMap != null)
      {
        cache.clear();
        cache.putAll(deserializedMap);
        lastModified = LocalDateTime.now();
      }
    }
  }
}

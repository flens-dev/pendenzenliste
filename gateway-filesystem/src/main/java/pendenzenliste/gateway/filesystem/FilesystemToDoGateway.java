package pendenzenliste.gateway.filesystem;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

import pendenzenliste.domain.todos.IdentityValueObject;
import pendenzenliste.domain.todos.ToDoEntity;
import pendenzenliste.gateway.ToDoGateway;

/**
 * A gateway that provides access to todos stored in the filesystem.
 */
public class FilesystemToDoGateway implements ToDoGateway
{
  private final FileStorage<Map<IdentityValueObject, ToDoEntity>> storage;

  /**
   * Creates a new instance.
   *
   * @param path The path.
   */
  public FilesystemToDoGateway(final String path)
  {
    storage = new FileStorage<>(requireNonNull(path, "The path may not be null"));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<ToDoEntity> findById(final IdentityValueObject id)
  {
    return Optional.ofNullable(storage.getOr(new ConcurrentHashMap<>())
        .getOrDefault(id, null));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean delete(final IdentityValueObject id)
  {
    final Map<IdentityValueObject, ToDoEntity> data = storage.getOr(new ConcurrentHashMap<>());

    final var removed = data.remove(id);

    if (removed != null)
    {
      storage.flushToDisk(data);
    }

    return removed != null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void store(final ToDoEntity todo)
  {
    final Map<IdentityValueObject, ToDoEntity> data = storage.getOr(new ConcurrentHashMap<>());

    data.put(todo.identity(), todo);

    storage.flushToDisk(data);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Stream<ToDoEntity> fetchAll()
  {
    return storage.getOr(new ConcurrentHashMap<>()).values().stream();
  }
}

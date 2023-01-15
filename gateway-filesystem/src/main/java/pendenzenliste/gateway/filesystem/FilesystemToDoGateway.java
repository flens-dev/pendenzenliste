package pendenzenliste.gateway.filesystem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

import pendenzenliste.gateway.ToDoGateway;
import pendenzenliste.messaging.EventBus;
import pendenzenliste.todos.model.IdentityValueObject;
import pendenzenliste.todos.model.ToDoAggregate;
import pendenzenliste.todos.model.ToDoDeletedEvent;
import pendenzenliste.todos.model.ToDoEvent;
import pendenzenliste.todos.model.ToDoEventEntity;

/**
 * A gateway that provides access to todos stored in the filesystem.
 */
public class FilesystemToDoGateway implements ToDoGateway
{
  private final FileStorage<Map<IdentityValueObject, ToDoAggregate>> storage;
  private final EventBus eventBus;

  /**
   * Creates a new instance.
   *
   * @param path     The path.
   * @param eventBus The event bus.
   */
  public FilesystemToDoGateway(final String path, final EventBus eventBus)
  {
    storage = new FileStorage<>(requireNonNull(path, "The path may not be null"));
    this.eventBus = requireNonNull(eventBus, "The event bus may not be null");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<ToDoAggregate> findById(final IdentityValueObject id)
  {
    return Optional.ofNullable(storage.getOr(new ConcurrentHashMap<>()).getOrDefault(id, null));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean delete(final IdentityValueObject id)
  {
    final Map<IdentityValueObject, ToDoAggregate> data = storage.getOr(new ConcurrentHashMap<>());

    final var removed = data.remove(id);

    if (removed != null)
    {
      storage.flushToDisk(data);
      eventBus.publish(new ToDoDeletedEvent(LocalDateTime.now(), id));
    }

    return removed != null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void store(final ToDoAggregate todo)
  {
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

    final Map<IdentityValueObject, ToDoAggregate> data = storage.getOr(new ConcurrentHashMap<>());

    data.put(todo.aggregateRoot().identity(), todo);

    storage.flushToDisk(data);

    eventQueue.forEach(eventBus::publish);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Stream<ToDoAggregate> fetchAll()
  {
    return storage.getOr(new ConcurrentHashMap<>()).values().stream();
  }
}

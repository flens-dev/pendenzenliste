package pendenzenliste.gateway.inmemory;

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
import pendenzenliste.todos.model.ToDoEvent;

/**
 * An implementation of the {@link ToDoGateway} interface that stores the ToDos in-memory.
 */
public final class InMemoryToDoGateway implements ToDoGateway
{
  private static final Map<IdentityValueObject, ToDoAggregate> STORE = new ConcurrentHashMap<>();
  private final EventBus eventBus;

  /**
   * Creates a new instance.
   *
   * @param eventBus The event bus that should be used by this instance.
   */
  public InMemoryToDoGateway(final EventBus eventBus)
  {
    this.eventBus = requireNonNull(eventBus, "The event bus may not be null");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<ToDoAggregate> findById(final IdentityValueObject id)
  {
    return Optional.ofNullable(STORE.getOrDefault(id, null));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean delete(final IdentityValueObject id)
  {
    if (STORE.containsKey(id))
    {
      STORE.remove(id);
      return true;
    }

    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void store(final ToDoAggregate todo)
  {
    final Collection<ToDoEvent> eventQueue = new ArrayList<>();

    todo.events()
        .stream()
        .filter(event -> event.identity() == null)
        .toList().forEach(event -> {
          todo.replaceEvent(event, event.withIdentity(IdentityValueObject.random()));
          eventQueue.add(event.event());
        });

    STORE.put(todo.aggregateRoot().identity(), todo);


    eventQueue.forEach(eventBus::publish);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Stream<ToDoAggregate> fetchAll()
  {
    return STORE.values().stream();
  }
}

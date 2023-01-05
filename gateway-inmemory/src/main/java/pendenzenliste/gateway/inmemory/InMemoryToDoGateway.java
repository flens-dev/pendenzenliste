package pendenzenliste.gateway.inmemory;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import pendenzenliste.domain.todos.IdentityValueObject;
import pendenzenliste.domain.todos.ToDoEntity;
import pendenzenliste.gateway.ToDoGateway;

/**
 * An implementation of the {@link ToDoGateway} interface that stores the ToDos in-memory.
 */
public final class InMemoryToDoGateway implements ToDoGateway
{
  private static final Map<IdentityValueObject, ToDoEntity> STORE = new ConcurrentHashMap<>();

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<ToDoEntity> findById(final IdentityValueObject id)
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
  public void store(final ToDoEntity todo)
  {
    STORE.put(todo.identity(), todo);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Stream<ToDoEntity> fetchAll()
  {
    return STORE.values().stream();
  }
}

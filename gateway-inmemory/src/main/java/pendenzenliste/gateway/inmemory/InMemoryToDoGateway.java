package pendenzenliste.gateway.inmemory;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import pendenzenliste.domain.ToDoEntity;
import pendenzenliste.domain.ToDoIdentityValueObject;
import pendenzenliste.gateway.ToDoGateway;

/**
 * An implementation of the {@link ToDoGateway} interface that stores the ToDos in-memory.
 */
public final class InMemoryToDoGateway implements ToDoGateway
{
  private static final Map<ToDoIdentityValueObject, ToDoEntity> STORE =
      new ConcurrentHashMap<>();

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<ToDoEntity> findById(final ToDoIdentityValueObject id)
  {
    return Optional.ofNullable(STORE.getOrDefault(id, null));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean delete(final ToDoIdentityValueObject id)
  {
    if (STORE.containsKey(id))
    {
      STORE.remove(id);
      return true;
    }

    return false;
  }
}

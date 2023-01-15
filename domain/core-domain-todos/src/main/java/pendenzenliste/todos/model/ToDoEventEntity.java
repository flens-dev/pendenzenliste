package pendenzenliste.todos.model;

import java.io.Serializable;

/**
 * An entity that can be used to represent a todo event.
 *
 * @param identity The identity of the event.
 * @param event    The event.
 */
public record ToDoEventEntity(IdentityValueObject identity, ToDoEvent event) implements Serializable
{
  /**
   * Creates a copy instance with the given id.
   *
   * @param identity The identity.
   * @return The copy.
   */
  public ToDoEventEntity withIdentity(final IdentityValueObject identity)
  {
    return new ToDoEventEntity(identity, event);
  }
}

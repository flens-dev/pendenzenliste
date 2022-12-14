package pendenzenliste.gateway;

import java.util.Optional;

import pendenzenliste.domain.ToDoEntity;
import pendenzenliste.domain.ToDoIdentityValueObject;

/**
 * A gateway that can be used to access ToDos
 */
public interface ToDoGateway
{
  /**
   * Attempts to find a specific ToDo by its id.
   *
   * @param id The ID.
   * @return The ToDo or {@link Optional#empty()}
   */
  Optional<ToDoEntity> findById(ToDoIdentityValueObject id);

  /**
   * Deletes the ToDo with the given ID.
   *
   * @param id The ID.
   * @return True if the ToDo has been deleted, otherwise false.
   */
  boolean delete(ToDoIdentityValueObject id);
}

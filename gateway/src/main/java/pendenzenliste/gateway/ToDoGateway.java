package pendenzenliste.gateway;

import java.util.Optional;
import java.util.stream.Stream;

import pendenzenliste.domain.IdentityValueObject;
import pendenzenliste.domain.ToDoEntity;

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
  Optional<ToDoEntity> findById(IdentityValueObject id);

  /**
   * Deletes the ToDo with the given ID.
   *
   * @param id The ID.
   * @return True if the ToDo has been deleted, otherwise false.
   */
  boolean delete(IdentityValueObject id);

  /**
   * Updates the todo.
   *
   * @param todo The todo.
   */
  void store(final ToDoEntity todo);

  /**
   * Fetches all the todos.
   *
   * @return The stream of all todos.
   */
  Stream<ToDoEntity> fetchAll();
}

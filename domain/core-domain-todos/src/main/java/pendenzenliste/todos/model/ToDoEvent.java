package pendenzenliste.todos.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * The common interface for objects that represent some kind of event in the context of a domain.
 */
public interface ToDoEvent extends Serializable
{
  /**
   * The timestamp when the event occurred.
   *
   * @return The timestamp.
   */
  LocalDateTime timestamp();

  /**
   * The identity of the todo that the event belongs to.
   *
   * @return The identity of the todo.
   */
  IdentityValueObject identity();

  /**
   * Visits the event with the given visitor.
   *
   * @param visitor The visitor.
   */
  void visit(ToDoEventVisitor visitor);
}

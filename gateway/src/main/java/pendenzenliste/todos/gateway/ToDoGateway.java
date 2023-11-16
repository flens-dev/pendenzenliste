package pendenzenliste.todos.gateway;

import pendenzenliste.todos.model.IdentityValueObject;
import pendenzenliste.todos.model.ToDoAggregate;
import pendenzenliste.todos.model.ToDoRepository;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * A gateway that can be used to access ToDos
 */
public interface ToDoGateway extends ToDoRepository {
}

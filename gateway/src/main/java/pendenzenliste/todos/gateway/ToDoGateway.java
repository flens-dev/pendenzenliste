package pendenzenliste.todos.gateway;

import pendenzenliste.todos.model.IdentityValueObject;
import pendenzenliste.todos.model.ToDoAggregate;
import pendenzenliste.todos.model.ToDoService;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * A gateway that can be used to access ToDos
 */
public interface ToDoGateway extends ToDoService {
    /**
     * Attempts to find a specific ToDo by its id.
     *
     * @param id The ID.
     * @return The ToDo or {@link Optional#empty()}
     */
    Optional<ToDoAggregate> findById(IdentityValueObject id);

    /**
     * Deletes the ToDo with the given ID.
     *
     * @param id The ID.
     * @return True if the ToDo has been deleted, otherwise false.
     */
    boolean delete(IdentityValueObject id);

    /**
     * Fetches all the todos.
     *
     * @return The stream of all todos.
     */
    Stream<ToDoAggregate> fetchAll();

    /**
     * Fetches all todos that have been completed before the given timestamp.
     *
     * @param timestamp The timestamp.
     * @return The todos that have been completed before the given timestamp.
     */
    Stream<ToDoAggregate> fetchAllCompletedBefore(LocalDateTime timestamp);
}

package pendenzenliste.todos.model;

/**
 * An application service that provides access to the todos.
 */
public interface ToDoService {

    /**
     * Updates the todo.
     *
     * @param todo The todo.
     */
    void store(final ToDoAggregate todo);
}

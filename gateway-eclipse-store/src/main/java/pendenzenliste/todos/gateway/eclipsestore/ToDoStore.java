package pendenzenliste.todos.gateway.eclipsestore;

import pendenzenliste.todos.model.ToDoEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The todo store used for the integration with eclipse store.
 */
public class ToDoStore {

    private List<ToDoEntity> todos = new ArrayList<>();

    /**
     * Retrieves the todos.
     *
     * @return The todos.
     */
    public List<ToDoEntity> getTodos() {
        return new ArrayList<>(todos);
    }

    /**
     * Sets the todos.
     *
     * @param todos The todos.
     */
    public void setTodos(final Collection<ToDoEntity> todos) {
        this.todos.clear();
        this.todos.addAll(todos);
    }
}

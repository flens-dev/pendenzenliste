package pendenzenliste.todos.boundary.in;

/**
 * A request that can be used to update a todo.
 *
 * @param identity    The identity of the todo.
 * @param headline    The new headline of the todo.
 * @param description The new description of the todo.
 */
public record UpdateToDoRequest(String identity, String headline, String description)
{
}

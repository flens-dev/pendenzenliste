package pendenzenliste.todos.boundary.in;

/**
 * A request that can be used to reopen a closed ToDo.
 *
 * @param identity The identity of the ToDo.
 */
public record ReopenToDoRequest(String identity) {
}

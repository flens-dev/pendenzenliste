package pendenzenliste.todos.boundary.in;

/**
 * A request that can be used to reset a closed ToDo.
 *
 * @param identity The identity of the ToDo.
 */
public record ResetToDoRequest(String identity)
{
}

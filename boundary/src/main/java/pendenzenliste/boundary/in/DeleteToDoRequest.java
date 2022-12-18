package pendenzenliste.boundary.in;

/**
 * A request that can be used to delete an existing ToDo.
 *
 * @param identity The identity of the todo that should be deleted.
 */
public record DeleteToDoRequest(String identity)
{
}

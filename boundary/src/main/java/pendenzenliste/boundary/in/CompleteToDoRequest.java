package pendenzenliste.boundary.in;

/**
 * A request that can be used to complete a ToDo.
 *
 * @param identity The identity of the ToDo.
 */
public record CompleteToDoRequest(String identity)
{
}

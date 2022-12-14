package pendenzenliste.ports.in;

/**
 * A request that can be used to fetch a ToDo.
 *
 * @param id The ID of the ToDo.
 */
public record FetchToDoRequest(String id)
{
}

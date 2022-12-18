package pendenzenliste.boundary.in;

/**
 * A request that can be used to create a new ToDo.
 *
 * @param headline    The headline.
 * @param description The description.
 */
public record CreateToDoRequest(String headline,
                                String description)
{
}

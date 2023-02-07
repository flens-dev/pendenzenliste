package pendenzenliste.dropwizard;

import java.util.Collection;
import java.util.Date;

/**
 * A JSON based representation of a todo.
 *
 * @param identity     The identity.
 * @param headline     The headline.
 * @param description  The description.
 * @param created      The created timestamp.
 * @param lastModified The last modified timestamp.
 * @param completed    The completed timestamp
 * @param state        The state.
 * @param capabilities The capabilities.
 */
public record JsonToDoViewModel(String identity, String headline, String description, Date created,
                                Date lastModified, Date completed, String state,
                                Collection<String> capabilities)
{
}

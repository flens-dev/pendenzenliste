package pendenzenliste.ports.out;

import java.time.LocalDateTime;

/**
 * A response model that can be used to represent todos in a list.
 *
 * @param identity     The identity.
 * @param headline     The headline.
 * @param description  The description.
 * @param created      The created timestamp.
 * @param lastModified The last modified timestamp.
 * @param completed    The completed timestamp.
 * @param state        The state.
 */
public record ToDoListResponseModel(String identity, String headline, String description,
                                    LocalDateTime created, LocalDateTime lastModified,
                                    LocalDateTime completed, String state)
{
}

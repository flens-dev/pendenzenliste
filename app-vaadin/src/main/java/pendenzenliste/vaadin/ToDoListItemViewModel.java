package pendenzenliste.vaadin;

import java.time.LocalDateTime;

/**
 * A view model that can be used to represent a todo in a list view.
 *
 * @param identity     The identity.
 * @param headline     The headline.
 * @param created      The created timestamp.
 * @param lastModified The last modified timestamp.
 * @param completed    The completed timestamp.
 * @param state        The current state.
 */
public record ToDoListItemViewModel(String identity, String headline, LocalDateTime created,
                                    LocalDateTime lastModified, LocalDateTime completed,
                                    String state)
{
}

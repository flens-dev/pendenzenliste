package pendenzenliste.javafx;

import java.time.LocalDateTime;

/**
 * A DTO that can be used to represent a todo in a list view.
 * <p>
 * TODO: Should this be a ViewModel? Is an additional DTO pattern necessary?
 *
 * @param identity     The identity.
 * @param headline     The headline
 * @param created      The created date.
 * @param lastModified The last modified date.
 * @param completed    The completed date.
 * @param state        The state of the todo.
 */
public record ToDoListDTO(String identity, String headline, LocalDateTime created,
                          LocalDateTime lastModified, LocalDateTime completed, String state)
{
}

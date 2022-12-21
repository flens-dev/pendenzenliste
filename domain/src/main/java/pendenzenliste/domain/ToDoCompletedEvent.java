package pendenzenliste.domain;

import java.time.LocalDateTime;

/**
 * An event that can be used to indicate that a todo has been completed.
 *
 * @param timestamp The timestamp.
 * @param identity  The identity of the todo.
 */
public record ToDoCompletedEvent(LocalDateTime timestamp, IdentityValueObject identity)
    implements ToDoEvent
{
}

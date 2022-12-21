package pendenzenliste.domain;

import java.time.LocalDateTime;

/**
 * An event that can be used to indicate that a todo has been reset.
 *
 * @param timestamp The timestamp.
 * @param identity  The identity of the todo.
 */
public record ToDoResetEvent(LocalDateTime timestamp, IdentityValueObject identity)
    implements ToDoEvent
{
}

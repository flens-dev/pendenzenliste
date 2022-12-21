package pendenzenliste.domain;

import java.time.LocalDateTime;

/**
 * An event that can be used to represent that a todo has been created.
 *
 * @param timestamp The timestamp.
 * @param identity  The identity.
 */
public record ToDoCreatedEvent(LocalDateTime timestamp, IdentityValueObject identity)
    implements ToDoEvent
{
}

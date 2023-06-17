package pendenzenliste.todos.model;

import java.io.Serializable;

/**
 * An entity that can be used to represent a ToDo.
 *
 * @param identity     The identity.
 * @param headline     The headline.
 * @param description  The description.
 * @param created      The created timestamp.
 * @param lastModified The last modified timestamp.
 * @param completed    The completed timestamp.
 * @param state        The state of the todo.
 */
public record ToDoEntity(IdentityValueObject identity, HeadlineValueObject headline,
                         DescriptionValueObject description, CreatedTimestampValueObject created,
                         LastModifiedTimestampValueObject lastModified,
                         CompletedTimestampValueObject completed, ToDoStateValueObject state)
    implements Entity<IdentityValueObject>, Serializable
{
}

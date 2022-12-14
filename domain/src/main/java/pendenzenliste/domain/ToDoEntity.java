package pendenzenliste.domain;

/**
 * An entity that can be used to represent a ToDo.
 *
 * @param identity     The identity.
 * @param headline     The headline.
 * @param description  The description.
 * @param created      The created timestamp.
 * @param lastModified The last modified timestamp.
 */
public record ToDoEntity(ToDoIdentityValueObject identity, HeadlineValueObject headline,
                         DescriptionValueObject description, CreatedTimestampValueObject created,
                         LastModifiedTimestampValueObject lastModified)
    implements Entity<ToDoIdentityValueObject>
{
}

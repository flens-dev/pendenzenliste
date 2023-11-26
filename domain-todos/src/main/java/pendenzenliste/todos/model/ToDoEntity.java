package pendenzenliste.todos.model;

import pendenzenliste.domain.util.Entity;

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
        implements Entity<IdentityValueObject>, Serializable {

    /**
     * Checks whether the todo is currently open.
     *
     * @return True if the todo is currently open, otherwise false.
     */
    public boolean isOpen() {
        return ToDoStateValueObject.OPEN.equals(state);
    }

    /**
     * Checks whether the todo has been completed.
     *
     * @return True if the todo has been completed, otherwise false.
     */
    public boolean isClosed() {
        return ToDoStateValueObject.COMPLETED.equals(state);
    }

    /**
     * Completes the todo.
     *
     * @return The completed todo.
     */
    public ToDoEntity complete() {
        return new ToDoEntity(identity, headline,
                description, created,
                LastModifiedTimestampValueObject.now(),
                CompletedTimestampValueObject.now(),
                ToDoStateValueObject.COMPLETED);
    }

    /**
     * Reopens the todo.
     *
     * @return The reopened todo.
     */
    public ToDoEntity reopen() {
        return new ToDoEntity(identity,
                headline,
                description,
                created,
                LastModifiedTimestampValueObject.now(),
                null,
                ToDoStateValueObject.OPEN);
    }

    /**
     * Changes the headline
     *
     * @param headline The headline.
     * @return The updated todo.
     */
    public ToDoEntity changeHeadline(HeadlineValueObject headline) {
        return new ToDoEntity(identity,
                headline,
                description,
                created,
                LastModifiedTimestampValueObject.now(),
                completed,
                state);
    }

    /**
     * Describes the todo.
     *
     * @param description The new description.
     * @return The updated todo.
     */
    public ToDoEntity describe(DescriptionValueObject description) {
        return new ToDoEntity(identity,
                headline,
                description,
                created,
                LastModifiedTimestampValueObject.now(),
                completed,
                state);
    }
}

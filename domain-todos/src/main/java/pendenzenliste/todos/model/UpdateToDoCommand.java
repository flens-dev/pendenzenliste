package pendenzenliste.todos.model;

/**
 * A command that can be used to update a todo.
 *
 * @param headline    The headline.
 * @param description The description.
 */
public record UpdateToDoCommand(HeadlineValueObject headline, DescriptionValueObject description) {
}

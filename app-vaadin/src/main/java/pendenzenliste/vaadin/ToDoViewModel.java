package pendenzenliste.vaadin;

/**
 * A view model that can be used to represent a todo.
 *
 * @param identity    The identity.
 * @param headline    The headline.
 * @param description The description.
 */
public record ToDoViewModel(String identity, String headline, String description)
{
}

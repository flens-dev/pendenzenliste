package pendenzenliste.javafx;

import pendenzenliste.todos.boundary.in.*;

import static java.util.Objects.requireNonNull;

/**
 * A controller that can be used in the context the application.
 */
public class ToDoController {
    private final ToDoInputBoundaryFactory factory;

    /**
     * Creates a new instance.
     *
     * @param factory The factory that should be used by this instance.
     */
    public ToDoController(final ToDoInputBoundaryFactory factory) {
        this.factory = requireNonNull(factory, "The factory may not be null");
    }

    /**
     * Loads the displayed todo for editing.
     *
     * @param identity The identity of the todo that should be loaded.
     */
    public void loadForEdit(final String identity) {
        final var request = new FetchToDoRequest(identity);

        factory.fetch().execute(request);
    }

    /**
     * Updates the specified todo.
     *
     * @param identity    The identity.
     * @param headline    The headline.
     * @param description The description.
     */
    public void update(final String identity, final String headline, final String description) {
        final var request = new UpdateToDoRequest(identity, headline, description);

        factory.update().execute(request);
    }

    /**
     * Creates a new todo.
     *
     * @param headline    The headline.
     * @param description The description.
     */
    public void create(final String headline, final String description) {
        final var request = new CreateToDoRequest(headline, description);

        factory.create().execute(request);
    }

    /**
     * Deletes a todo.
     *
     * @param identity The identity.
     */
    public void delete(final String identity) {
        final var request = new DeleteToDoRequest(identity);

        factory.delete().execute(request);
    }

    /**
     * Completes a todo.
     *
     * @param identity The identity.
     */
    public void complete(final String identity) {
        final var request = new CompleteToDoRequest(identity);

        factory.complete().execute(request);
    }

    /**
     * Resets a todo.
     *
     * @param identity The identity.
     */
    public void reset(final String identity) {
        final var request = new ReopenToDoRequest(identity);

        factory.reopen().execute(request);
    }

    /**
     * Subscribes the todo list.
     */
    public void subscribe() {
        final var request = new SubscribeToDoListRequest();

        factory.subscribe().execute(request);
    }
}

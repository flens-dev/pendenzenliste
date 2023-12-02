package pendenzenliste.cli;

import pendenzenliste.todos.boundary.out.FetchToDoListFailedResponse;
import pendenzenliste.todos.boundary.out.FetchToDoListOutputBoundary;
import pendenzenliste.todos.boundary.out.FetchedToDoListResponse;
import pendenzenliste.todos.boundary.out.ToDoListResponseModel;

import static java.util.Objects.requireNonNull;

/**
 * A presenter that can be used to handle the results of a fetch todo list request.
 */
public class FetchToDoListPresenter implements FetchToDoListOutputBoundary {
    private final CommandLineResult result;

    /**
     * Creates a new instance.
     *
     * @param result The result that should be used by this instance.
     */
    public FetchToDoListPresenter(final CommandLineResult result) {
        this.result = requireNonNull(result, "The result may not be null");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleFailedResponse(final FetchToDoListFailedResponse response) {
        result.write(response.reason());
        result.exitGeneralFailure();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleSuccessfulResponse(final FetchedToDoListResponse response) {
        for (final ToDoListResponseModel todo : response.todos()) {
            result.write("Identity: " + todo.identity());
            result.write("Headline: " + todo.headline());
            result.write("State: " + todo.state());
            result.write("Created: " + todo.created());
            result.write("LastModified: " + todo.lastModified());
            result.write("Completed: " + todo.completed());

            result.write("--------------------------------------------");
            result.write(todo.description());
            result.write("--------------------------------------------");
            result.writeNewLine();
        }

        result.exitSuccessful();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDetached() {
        return false;
    }
}

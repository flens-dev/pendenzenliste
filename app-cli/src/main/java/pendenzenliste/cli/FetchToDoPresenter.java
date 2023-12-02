package pendenzenliste.cli;

import pendenzenliste.todos.boundary.out.FetchToDoFailedResponse;
import pendenzenliste.todos.boundary.out.FetchToDoOutputBoundary;
import pendenzenliste.todos.boundary.out.ToDoFetchedResponse;

import static java.util.Objects.requireNonNull;

/**
 * A presenter that can be used to handle the results of a fetch todo request.
 */
public class FetchToDoPresenter implements FetchToDoOutputBoundary {
    private final CommandLineResult result;

    /**
     * Creates a new instance.
     *
     * @param result The result that should be used by this instance.
     */
    public FetchToDoPresenter(final CommandLineResult result) {
        this.result = requireNonNull(result, "The result may not be null");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleFailedResponse(final FetchToDoFailedResponse response) {
        result.write(response.reason());
        result.exitGeneralFailure();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleSuccessfulResponse(final ToDoFetchedResponse response) {
        result.write("Identity: " + response.identity());
        result.write("Headline: " + response.headline());
        result.write("State: " + response.state());
        result.write("Created: " + response.created());
        result.write("LastModified: " + response.lastModified());
        result.write("Completed: " + response.completed());

        result.write("--------------------------------------------");
        result.write(response.description());
        result.write("--------------------------------------------");
        result.writeNewLine();
        result.exitNoError();
    }
}

package pendenzenliste.cli;

import pendenzenliste.todos.boundary.out.CreateToDoOutputBoundary;
import pendenzenliste.todos.boundary.out.ToDoCreatedResponse;
import pendenzenliste.todos.boundary.out.ToDoCreationFailedResponse;

import static java.util.Objects.requireNonNull;

/**
 * A presenter that can be used to handle the results of a create todo request.
 */
public class CreateToDoPresenter implements CreateToDoOutputBoundary {
    private final CommandLineResult result;

    /**
     * Creates a new instance.
     *
     * @param result The result that should be updated by the presenter.
     */
    public CreateToDoPresenter(final CommandLineResult result) {

        this.result = requireNonNull(result, "The result may not be null");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleSuccessfulResponse(final ToDoCreatedResponse response) {
        result.write(response.identity());
        result.exitSuccessful();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleFailedResponse(final ToDoCreationFailedResponse response) {
        result.write(response.reason());
        result.exitGeneralFailure();
    }
}

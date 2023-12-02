package pendenzenliste.cli;

import pendenzenliste.todos.boundary.out.ToDoUpdateFailedResponse;
import pendenzenliste.todos.boundary.out.ToDoUpdatedResponse;
import pendenzenliste.todos.boundary.out.UpdateToDoOutputBoundary;

import static java.util.Objects.requireNonNull;

/**
 * A presenter that can be used to handle the results of a update todo request.
 */
public class UpdateToDoPresenter implements UpdateToDoOutputBoundary {
    private final CommandLineResult result;

    /**
     * Creates a new instance.
     *
     * @param result The result.
     */
    public UpdateToDoPresenter(final CommandLineResult result) {
        this.result = requireNonNull(result, "The result may not be null");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleSuccessfulResponse(final ToDoUpdatedResponse response) {
        result.exitSuccessful();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleFailedResponse(final ToDoUpdateFailedResponse response) {
        result.write(response.reason());
        result.exitGeneralFailure();
    }
}

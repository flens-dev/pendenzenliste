package pendenzenliste.dropwizard;

import io.dropwizard.jersey.errors.ErrorMessage;
import pendenzenliste.todos.boundary.out.CreateToDoOutputBoundary;
import pendenzenliste.todos.boundary.out.ToDoCreatedResponse;
import pendenzenliste.todos.boundary.out.ToDoCreationFailedResponse;

import javax.ws.rs.core.Response;

import static java.util.Objects.requireNonNull;

/**
 * A presenter that can be used to build JSON based representations for the create todo use case.
 */
public class JsonCreateToDoPresenter implements CreateToDoOutputBoundary {

    private final HttpResponseViewModel viewModel;

    /**
     * Creates a new instance.
     *
     * @param viewModel The view model.
     */
    public JsonCreateToDoPresenter(final HttpResponseViewModel viewModel) {
        this.viewModel = requireNonNull(viewModel, "The view model may not be null");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleSuccessfulResponse(final ToDoCreatedResponse response) {
        viewModel.response = Response.status(Response.Status.CREATED)
                .entity(new JsonCreatedToDoData(response.identity()))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleFailedResponse(final ToDoCreationFailedResponse response) {
        viewModel.response = Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorMessage("Failed to create the todo"))
                .build();
    }
}

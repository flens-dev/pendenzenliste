package pendenzenliste.dropwizard;

import io.dropwizard.jersey.errors.ErrorMessage;
import pendenzenliste.todos.boundary.out.ToDoUpdateFailedResponse;
import pendenzenliste.todos.boundary.out.ToDoUpdatedResponse;
import pendenzenliste.todos.boundary.out.UpdateToDoOutputBoundary;

import javax.ws.rs.core.Response;

import static java.util.Objects.requireNonNull;

/**
 * An implementation of the {@link UpdateToDoOutputBoundary} that builds a JSON representation.
 */
public class JsonUpdateToDoPresenter implements UpdateToDoOutputBoundary {

    private final HttpResponseViewModel viewModel;

    /**
     * Creates a new instance.
     *
     * @param viewModel The view model that should be used by this instance.
     */
    public JsonUpdateToDoPresenter(final HttpResponseViewModel viewModel) {
        this.viewModel = requireNonNull(viewModel, "The view model may not be null");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleSuccessfulResponse(final ToDoUpdatedResponse response) {
        viewModel.response = Response.ok().build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleFailedResponse(final ToDoUpdateFailedResponse response) {
        viewModel.response =
                Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(response.reason()))
                        .build();
    }
}

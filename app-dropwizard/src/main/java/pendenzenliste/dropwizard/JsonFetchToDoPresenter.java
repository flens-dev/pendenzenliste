package pendenzenliste.dropwizard;

import javax.ws.rs.core.Response;

import static java.util.Objects.requireNonNull;
import static pendenzenliste.dropwizard.DateMappingUtils.toDate;

import io.dropwizard.jersey.errors.ErrorMessage;
import pendenzenliste.todos.boundary.out.FetchToDoFailedResponse;
import pendenzenliste.todos.boundary.out.FetchToDoOutputBoundary;
import pendenzenliste.todos.boundary.out.ToDoFetchedResponse;

/**
 * A presenter that can be used to present the JSON based results of a fetch todo request.
 */
public class JsonFetchToDoPresenter implements FetchToDoOutputBoundary
{
  private final HttpResponseViewModel viewModel;

  /**
   * Creates a new instance.
   *
   * @param viewModel The view model that should be updated by this instance.
   */
  public JsonFetchToDoPresenter(final HttpResponseViewModel viewModel)
  {
    this.viewModel = requireNonNull(viewModel, "The view model may not be null");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void handleFailedResponse(final FetchToDoFailedResponse response)
  {
    viewModel.response =
        Response.status(Response.Status.NOT_FOUND).entity(new ErrorMessage(response.reason()))
            .build();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void handleSuccessfulResponse(final ToDoFetchedResponse response)
  {
    viewModel.response = Response.ok(
        new JsonToDoViewModel(response.identity(), response.headline(), response.description(),
            toDate(response.created()), toDate(response.lastModified()),
            toDate(response.completed()), response.state(), response.capabilities())).build();
  }
}

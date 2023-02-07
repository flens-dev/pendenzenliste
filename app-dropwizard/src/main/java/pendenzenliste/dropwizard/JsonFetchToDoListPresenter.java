package pendenzenliste.dropwizard;

import javax.ws.rs.core.Response;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;
import static pendenzenliste.dropwizard.DateMappingUtils.toDate;

import io.dropwizard.jersey.errors.ErrorMessage;
import pendenzenliste.todos.boundary.out.FetchToDoListFailedResponse;
import pendenzenliste.todos.boundary.out.FetchToDoListOutputBoundary;
import pendenzenliste.todos.boundary.out.FetchedToDoListResponse;
import pendenzenliste.todos.boundary.out.ToDoListResponseModel;

/**
 * A presenter that can be used to build JSON based response messages.
 */
public class JsonFetchToDoListPresenter implements FetchToDoListOutputBoundary
{
  private final HttpResponseViewModel viewModel;

  /**
   * Creates a new instance.
   *
   * @param viewModel The view model that should be updated by the presenter.
   */
  public JsonFetchToDoListPresenter(final HttpResponseViewModel viewModel)
  {
    this.viewModel = requireNonNull(viewModel, "The view model may not be null");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void handleFailedResponse(final FetchToDoListFailedResponse response)
  {
    viewModel.response =
        Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(response.reason()))
            .build();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void handleSuccessfulResponse(final FetchedToDoListResponse response)
  {
    viewModel.response =
        Response.ok().entity(response.todos().stream().map(mapToViewModel()).toList()).build();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isDetached()
  {
    return false;
  }


  /**
   * Maps the response model to a view model.
   *
   * @return The mapping function.
   */
  private static Function<ToDoListResponseModel, JsonToDoViewModel> mapToViewModel()
  {
    return todo -> new JsonToDoViewModel(todo.identity(), todo.headline(), todo.description(),
        toDate(todo.created()), toDate(todo.lastModified()), toDate(todo.completed()), todo.state(),
        todo.capabilities());
  }
}

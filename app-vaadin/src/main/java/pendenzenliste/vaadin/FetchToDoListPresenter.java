package pendenzenliste.vaadin;

import static java.util.Objects.requireNonNull;
import static pendenzenliste.vaadin.ViewModelMappingUtils.mapToViewModel;

import pendenzenliste.boundary.out.FetchToDoListFailedResponse;
import pendenzenliste.boundary.out.FetchToDoListOutputBoundary;
import pendenzenliste.boundary.out.FetchedToDoListResponse;

/**
 * A presenter that can be used to handle the results of a fetch todo list request.
 */
public class FetchToDoListPresenter implements FetchToDoListOutputBoundary
{
  private final ToDoListViewModel viewModel;

  /**
   * Creates a new instance.
   *
   * @param viewModel The view model that should be used by this instance.
   */
  public FetchToDoListPresenter(final ToDoListViewModel viewModel)
  {
    this.viewModel = requireNonNull(viewModel, "The view model may not be null");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void handleFailedResponse(final FetchToDoListFailedResponse response)
  {
    viewModel.errorMessage.set(response.reason());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void handleSuccessfulResponse(final FetchedToDoListResponse response)
  {
    viewModel.todos.set(response.todos().stream().map(mapToViewModel()).toList());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isDetached()
  {
    return viewModel.detached.get();
  }
}

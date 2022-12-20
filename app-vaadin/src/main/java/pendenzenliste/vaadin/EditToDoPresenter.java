package pendenzenliste.vaadin;

import static java.util.Objects.requireNonNull;

import pendenzenliste.boundary.out.FetchToDoFailedResponse;
import pendenzenliste.boundary.out.FetchToDoOutputBoundary;
import pendenzenliste.boundary.out.ToDoFetchedResponse;

/**
 * A presenter that can be used to edit a todo.
 */
public class EditToDoPresenter implements FetchToDoOutputBoundary
{
  private final ToDoListViewModel viewModel;

  /**
   * Creates a new instance.
   *
   * @param viewModel The view model that should be used by this instance.
   */
  public EditToDoPresenter(final ToDoListViewModel viewModel)
  {
    this.viewModel = requireNonNull(viewModel, "The view model may not be null");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void handleFailedResponse(final FetchToDoFailedResponse response)
  {
    viewModel.errorMessage.set(response.reason());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void handleSuccessfulResponse(final ToDoFetchedResponse response)
  {
    viewModel.identity.set(response.identity());
    viewModel.headline.set(response.headline());
    viewModel.description.set(response.description());
  }
}

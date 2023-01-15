package pendenzenliste.javafx;

import static java.util.Objects.requireNonNull;

import pendenzenliste.todos.boundary.out.FetchToDoFailedResponse;
import pendenzenliste.todos.boundary.out.FetchToDoOutputBoundary;
import pendenzenliste.todos.boundary.out.ToDoFetchedResponse;

/**
 * A presenter that can be used to edit a todo.
 */
public class EditToDoPresenter implements FetchToDoOutputBoundary
{
  private final EditToDoViewModel viewModel;

  /**
   * Creates a new instance.
   *
   * @param viewModel The view model that should be used by this instance.
   */
  public EditToDoPresenter(final EditToDoViewModel viewModel)
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
    viewModel.errorMessage.set("");
    viewModel.completeButtonVisible.set(response.capabilities().contains("COMPLETE"));
    viewModel.deleteButtonVisible.set(response.capabilities().contains("DELETE"));
    viewModel.resetButtonVisible.set(response.capabilities().contains("RESET"));
  }
}

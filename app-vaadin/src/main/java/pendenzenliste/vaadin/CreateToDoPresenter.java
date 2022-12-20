package pendenzenliste.vaadin;

import static java.util.Objects.requireNonNull;

import pendenzenliste.boundary.out.CreateToDoOutputBoundary;
import pendenzenliste.boundary.out.ToDoCreatedResponse;
import pendenzenliste.boundary.out.ToDoCreationFailedResponse;

/**
 * A presenter that can be used to handle the results of a create todo request.
 */
public class CreateToDoPresenter implements CreateToDoOutputBoundary
{
  private final ToDoListViewModel viewModel;

  /**
   * Creates a new instance.
   *
   * @param viewModel The view model that should be used by this instance.
   */
  public CreateToDoPresenter(final ToDoListViewModel viewModel)
  {
    this.viewModel = requireNonNull(viewModel, "The view model may not be null");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void handleSuccessfulResponse(final ToDoCreatedResponse response)
  {
    viewModel.clearEditor();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void handleFailedResponse(final ToDoCreationFailedResponse response)
  {
    viewModel.errorMessage.set(response.reason());
  }
}

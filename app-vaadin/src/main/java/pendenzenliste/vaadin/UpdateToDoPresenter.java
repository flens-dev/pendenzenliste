package pendenzenliste.vaadin;

import static java.util.Objects.requireNonNull;

import pendenzenliste.ports.out.ToDoUpdateFailedResponse;
import pendenzenliste.ports.out.ToDoUpdatedResponse;
import pendenzenliste.ports.out.UpdateToDoOutputBoundary;

/**
 * A presenter that can be used to handle the results of an update todo request.
 */
public class UpdateToDoPresenter implements UpdateToDoOutputBoundary
{
  private final ToDoView view;

  /**
   * Creates a new instance.
   *
   * @param view The view that should be used by this instance.
   */
  public UpdateToDoPresenter(final ToDoView view)
  {
    this.view = requireNonNull(view, "The view may not be null");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void handleSuccessfulResponse(final ToDoUpdatedResponse response)
  {
    view.clearEditor();
    view.loadToDos();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void handleFailedResponse(final ToDoUpdateFailedResponse response)
  {
    view.showGenericErrorMessage(response.reason());
  }
}

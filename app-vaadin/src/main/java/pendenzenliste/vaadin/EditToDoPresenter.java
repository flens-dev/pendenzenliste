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
  private final ToDoView view;

  /**
   * Creates a new instance.
   *
   * @param view The view that should be used by this instance.
   */
  public EditToDoPresenter(final ToDoView view)
  {
    this.view = requireNonNull(view, "The view may not be null");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void handleFailedResponse(final FetchToDoFailedResponse response)
  {
    view.showGenericErrorMessage(response.reason());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void handleSuccessfulResponse(final ToDoFetchedResponse response)
  {
    view.setSelectedToDo(new ToDoViewModel(
        response.identity(),
        response.headline(),
        response.description()
    ));
  }
}

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
  private final ToDoView view;

  /**
   * Creates a new instance.
   *
   * @param view The view that should be used by this instance.
   */
  public FetchToDoListPresenter(final ToDoView view)
  {
    this.view = requireNonNull(view, "The view may not be null");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void handleFailedResponse(final FetchToDoListFailedResponse response)
  {
    view.showGenericErrorMessage(response.reason());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void handleSuccessfulResponse(final FetchedToDoListResponse response)
  {
    view.setToDos(response.todos().stream().map(mapToViewModel()).toList());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isDetached()
  {
    return !view.isAttached();
  }
}

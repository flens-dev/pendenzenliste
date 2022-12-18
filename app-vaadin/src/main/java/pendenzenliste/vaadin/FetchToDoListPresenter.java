package pendenzenliste.vaadin;

import java.util.function.Function;

import static java.util.Objects.requireNonNull;

import pendenzenliste.ports.out.FetchToDoListFailedResponse;
import pendenzenliste.ports.out.FetchToDoListOutputBoundary;
import pendenzenliste.ports.out.FetchedToDoListResponse;
import pendenzenliste.ports.out.ToDoListResponseModel;

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
   * Maps the given response model to an appropriate view model.
   *
   * @return The mapping function.
   */
  private static Function<ToDoListResponseModel, ToDoListItemViewModel> mapToViewModel()
  {
    return todo -> new ToDoListItemViewModel(todo.identity(), todo.headline(), todo.created(),
        todo.lastModified(), todo.completed(), todo.state(),
        todo.capabilities().contains("DELETE"),
        todo.capabilities().contains("UPDATE"),
        todo.capabilities().contains("COMPLETE"),
        todo.capabilities().contains("RESET"));
  }
}

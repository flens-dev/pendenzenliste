package pendenzenliste.vaadin;

import java.util.function.Function;

import static java.util.Objects.requireNonNull;

import pendenzenliste.boundary.out.FetchToDoListFailedResponse;
import pendenzenliste.boundary.out.FetchToDoListOutputBoundary;
import pendenzenliste.boundary.out.FetchedToDoListResponse;
import pendenzenliste.boundary.out.ToDoListResponseModel;

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
   * Maps the given response model to an appropriate view model.
   *
   * @return The mapping function.
   */
  private static Function<ToDoListResponseModel, ToDoListItemViewModel> mapToViewModel()
  {
    return todo -> {
      final var viewModel = new ToDoListItemViewModel();

      viewModel.identity.set(todo.identity());
      viewModel.headline.set(todo.headline());
      viewModel.created.set(todo.created());
      viewModel.lastModified.set(todo.lastModified());
      viewModel.completed.set(todo.completed());
      viewModel.state.set(todo.state());
      viewModel.deletable.set(todo.capabilities().contains("DELETE"));
      viewModel.editable.set(todo.capabilities().contains("UPDATE"));
      viewModel.completable.set(todo.capabilities().contains("COMPLETE"));
      viewModel.resettable.set(todo.capabilities().contains("RESET"));

      return viewModel;
    };
  }
}

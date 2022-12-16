package pendenzenliste.javafx;

import java.util.function.Function;

import static java.util.Objects.requireNonNull;

import pendenzenliste.ports.out.FetchToDoListFailedResponse;
import pendenzenliste.ports.out.FetchToDoListOutputBoundary;
import pendenzenliste.ports.out.FetchedToDoListResponse;
import pendenzenliste.ports.out.ToDoListResponseModel;

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
    this.viewModel = requireNonNull(viewModel, "The viewModel may not be null");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void handleFailedResponse(final FetchToDoListFailedResponse response)
  {
    //TODO: Show some kind of error message
    System.out.println(response.reason());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void handleSuccessfulResponse(final FetchedToDoListResponse response)
  {
    viewModel.todos.setAll(response.todos().stream().map(mapToDTO()).toList());
  }

  /**
   * Maps the given response model to a DTO.
   *
   * @return The mapping function.
   */
  private static Function<ToDoListResponseModel, ToDoListItemViewModel> mapToDTO()
  {
    return todo -> new ToDoListItemViewModel(todo.identity(), todo.headline(), todo.created(),
        todo.lastModified(), todo.completed(), todo.state());
  }
}

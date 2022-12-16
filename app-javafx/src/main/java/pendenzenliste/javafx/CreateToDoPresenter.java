package pendenzenliste.javafx;

import java.time.LocalDateTime;

import static java.util.Objects.requireNonNull;

import pendenzenliste.ports.out.CreateToDoOutputBoundary;
import pendenzenliste.ports.out.ToDoCreatedResponse;
import pendenzenliste.ports.out.ToDoCreationFailedResponse;

/**
 * A presenter that can be used to handle the results of a create todo request.
 */
public class CreateToDoPresenter implements CreateToDoOutputBoundary
{
  private final ToDoListViewModel listViewModel;
  private final EditToDoViewModel editViewModel;

  /**
   * Creates a new instance.
   *
   * @param listViewModel The view that should be used by this instance.
   */
  public CreateToDoPresenter(final ToDoListViewModel listViewModel,
                             final EditToDoViewModel editViewModel)
  {
    this.listViewModel = requireNonNull(listViewModel, "The view model may not be null");
    this.editViewModel = requireNonNull(editViewModel, "The edit view model may not be null");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void handleSuccessfulResponse(final ToDoCreatedResponse response)
  {
    editViewModel.clearedTrigger.set(LocalDateTime.now());
    listViewModel.listUpdated.set(LocalDateTime.now());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void handleFailedResponse(final ToDoCreationFailedResponse response)
  {
    editViewModel.errorMessage.set(response.reason());
  }
}

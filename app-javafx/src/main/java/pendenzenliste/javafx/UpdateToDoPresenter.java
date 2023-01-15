package pendenzenliste.javafx;

import java.time.LocalDateTime;

import static java.util.Objects.requireNonNull;

import pendenzenliste.todos.boundary.out.ToDoUpdateFailedResponse;
import pendenzenliste.todos.boundary.out.ToDoUpdatedResponse;
import pendenzenliste.todos.boundary.out.UpdateToDoOutputBoundary;

/**
 * A presenter that can be used to handle the results of an update todo request.
 */
public class UpdateToDoPresenter implements UpdateToDoOutputBoundary
{
  private final EditToDoViewModel editViewModel;

  /**
   * Creates a new instance.
   *
   * @param editViewModel The edit view model that should be used by this instance.
   */
  public UpdateToDoPresenter(final EditToDoViewModel editViewModel)
  {
    this.editViewModel = requireNonNull(editViewModel, "The edit view model may not be null");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void handleSuccessfulResponse(final ToDoUpdatedResponse response)
  {
    editViewModel.publishEvent(new ClearEditorRequestedEvent(LocalDateTime.now()));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void handleFailedResponse(final ToDoUpdateFailedResponse response)
  {
    editViewModel.errorMessage.set(response.reason());
  }
}

package pendenzenliste.javafx;

import java.time.LocalDateTime;

import static java.util.Objects.requireNonNull;

import pendenzenliste.boundary.out.CreateToDoOutputBoundary;
import pendenzenliste.boundary.out.ToDoCreatedResponse;
import pendenzenliste.boundary.out.ToDoCreationFailedResponse;

/**
 * A presenter that can be used to handle the results of a create todo request.
 */
public class CreateToDoPresenter implements CreateToDoOutputBoundary
{
  private final EditToDoViewModel editViewModel;

  /**
   * Creates a new instance.
   *
   * @param editViewModel The view that should be used by this instance.
   */
  public CreateToDoPresenter(final EditToDoViewModel editViewModel)
  {
    this.editViewModel = requireNonNull(editViewModel, "The edit view model may not be null");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void handleSuccessfulResponse(final ToDoCreatedResponse response)
  {
    editViewModel.publishEvent(new ClearEditorRequestedEvent(LocalDateTime.now()));
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

package pendenzenliste.cli;

import pendenzenliste.todos.boundary.out.ToDoUpdateFailedResponse;
import pendenzenliste.todos.boundary.out.ToDoUpdatedResponse;
import pendenzenliste.todos.boundary.out.UpdateToDoOutputBoundary;

/**
 * A presenter that can be used to handle the results of a update todo request.
 */
public class UpdateToDoPresenter implements UpdateToDoOutputBoundary
{
  /**
   * {@inheritDoc}
   */
  @Override
  public void handleSuccessfulResponse(final ToDoUpdatedResponse response)
  {
    System.exit(0);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void handleFailedResponse(final ToDoUpdateFailedResponse response)
  {
    System.out.println(response.reason());
    System.exit(1);
  }
}

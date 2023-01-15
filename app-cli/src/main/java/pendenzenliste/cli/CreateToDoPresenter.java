package pendenzenliste.cli;

import pendenzenliste.todos.boundary.out.CreateToDoOutputBoundary;
import pendenzenliste.todos.boundary.out.ToDoCreatedResponse;
import pendenzenliste.todos.boundary.out.ToDoCreationFailedResponse;

/**
 * A presenter that can be used to handle the results of a create todo request.
 */
public class CreateToDoPresenter implements CreateToDoOutputBoundary
{
  /**
   * {@inheritDoc}
   */
  @Override
  public void handleSuccessfulResponse(ToDoCreatedResponse response)
  {
    System.out.println(response.identity());
    System.exit(0);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void handleFailedResponse(final ToDoCreationFailedResponse response)
  {
    System.out.println(response.reason());
    System.exit(1);
  }
}

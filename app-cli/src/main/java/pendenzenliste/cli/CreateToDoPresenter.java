package pendenzenliste.cli;

import pendenzenliste.boundary.out.CreateToDoOutputBoundary;
import pendenzenliste.boundary.out.ToDoCreatedResponse;
import pendenzenliste.boundary.out.ToDoCreationFailedResponse;

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

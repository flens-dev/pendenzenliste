package pendenzenliste.cli;

import pendenzenliste.todos.boundary.out.FetchToDoFailedResponse;
import pendenzenliste.todos.boundary.out.FetchToDoOutputBoundary;
import pendenzenliste.todos.boundary.out.ToDoFetchedResponse;

/**
 * A presenter that can be used to handle the results of a fetch todo request.
 */
public class FetchToDoPresenter implements FetchToDoOutputBoundary
{
  /**
   * {@inheritDoc}
   */
  @Override
  public void handleFailedResponse(final FetchToDoFailedResponse response)
  {
    System.out.println(response.reason());
    System.exit(0);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void handleSuccessfulResponse(final ToDoFetchedResponse response)
  {
    System.out.println("Identity: " + response.identity());
    System.out.println("Headline: " + response.headline());
    System.out.println("State: " + response.state());
    System.out.println("Created: " + response.created());
    System.out.println("LastModified: " + response.lastModified());
    System.out.println("Completed: " + response.completed());

    System.out.println("--------------------------------------------");
    System.out.println(response.description());
    System.out.println("--------------------------------------------");
    System.out.println();
  }
}

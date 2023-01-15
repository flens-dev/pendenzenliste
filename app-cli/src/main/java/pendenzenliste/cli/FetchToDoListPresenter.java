package pendenzenliste.cli;

import pendenzenliste.todos.boundary.out.FetchToDoListFailedResponse;
import pendenzenliste.todos.boundary.out.FetchToDoListOutputBoundary;
import pendenzenliste.todos.boundary.out.FetchedToDoListResponse;
import pendenzenliste.todos.boundary.out.ToDoListResponseModel;

/**
 * A presenter that can be used to handle the results of a fetch todo list request.
 */
public class FetchToDoListPresenter implements FetchToDoListOutputBoundary
{
  /**
   * {@inheritDoc}
   */
  @Override
  public void handleFailedResponse(final FetchToDoListFailedResponse response)
  {
    System.out.println(response.reason());
    System.exit(0);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void handleSuccessfulResponse(final FetchedToDoListResponse response)
  {
    for (final ToDoListResponseModel todo : response.todos())
    {
      System.out.println("Identity: " + todo.identity());
      System.out.println("Headline: " + todo.headline());
      System.out.println("State: " + todo.state());
      System.out.println("Created: " + todo.created());
      System.out.println("LastModified: " + todo.lastModified());
      System.out.println("Completed: " + todo.completed());

      System.out.println("--------------------------------------------");
      System.out.println(todo.description());
      System.out.println("--------------------------------------------");
      System.out.println();
    }

    System.exit(0);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isDetached()
  {
    return false;
  }
}

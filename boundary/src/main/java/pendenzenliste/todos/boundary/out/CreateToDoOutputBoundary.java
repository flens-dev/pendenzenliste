package pendenzenliste.todos.boundary.out;

import pendenzenliste.boundary.out.OutputBoundary;

/**
 * An output boundary that can be used to handle the results of a create todo request.
 */
public interface CreateToDoOutputBoundary
    extends OutputBoundary<CreateToDoOutputBoundary, CreateToDoResponse>
{
  /**
   * Handles a successful response.
   *
   * @param response The response.
   */
  void handleSuccessfulResponse(ToDoCreatedResponse response);

  /**
   * Handles a failed response.
   *
   * @param response The response.
   */
  void handleFailedResponse(ToDoCreationFailedResponse response);
}

package pendenzenliste.boundary.out;

/**
 * An output boundary that can be used to handle the results of a todo update.
 */
public interface UpdateToDoOutputBoundary
    extends OutputBoundary<UpdateToDoOutputBoundary, UpdateToDoResponse>
{
  /**
   * Handles a successful response.
   *
   * @param response The response.
   */
  void handleSuccessfulResponse(ToDoUpdatedResponse response);

  /**
   * Handles a failed response.
   *
   * @param response The response.
   */
  void handleFailedResponse(ToDoUpdateFailedResponse response);
}

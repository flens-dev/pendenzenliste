package pendenzenliste.ports.out;

/**
 * An output boundary that can be used to handle the results of a fetch ToDo request.
 */
public interface FetchToDoOutputBoundary
    extends OutputBoundary<FetchToDoOutputBoundary, FetchToDoResponse>
{
  /**
   * Handles a failed response.
   *
   * @param response The response.
   */
  void handleFailedResponse(FetchToDoFailedResponse response);

  /**
   * Handles a successful response.
   *
   * @param response The response.
   */
  void handleSuccessfulResponse(ToDoFetchedResponse response);
}

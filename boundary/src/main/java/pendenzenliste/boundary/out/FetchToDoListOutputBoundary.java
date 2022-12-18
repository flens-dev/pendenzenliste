package pendenzenliste.boundary.out;

/**
 * An output boundary that can be used to handle the results of a list todos request.
 */
public interface FetchToDoListOutputBoundary
    extends OutputBoundary<FetchToDoListOutputBoundary, FetchToDoListResponse>
{
  /**
   * Handles a failed response.
   *
   * @param response The response.
   */
  void handleFailedResponse(FetchToDoListFailedResponse response);

  /**
   * Handles a successful response.
   *
   * @param response The response.
   */
  void handleSuccessfulResponse(FetchedToDoListResponse response);
}

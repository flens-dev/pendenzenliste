package pendenzenliste.ports.out;

/**
 * An output boundary that can be used to handle the results of a list todos request.
 */
public interface ListToDosOutputBoundary
    extends OutputBoundary<ListToDosOutputBoundary, ListToDosResponse>
{
  /**
   * Handles a failed response.
   *
   * @param response The response.
   */
  void handleFailedResponse(ListToDosFailedResponse response);

  /**
   * Handles a successful response.
   *
   * @param response The response.
   */
  void handleSuccessfulResponse(ToDoListResponse response);
}

package pendenzenliste.ports.out;

/**
 * A factory that can be used to create output boundaries.
 */
public interface ToDoOutputBoundaryFactory
{
  /**
   * Builds the output boundary used to handle the results of a create todo request.
   *
   * @return The output boundary.
   */
  CreateToDoOutputBoundary create();

  /**
   * Builds the output boundary used to handle the results of a fetch todo list request.
   *
   * @return The output boundary.
   */
  FetchToDoListOutputBoundary list();

  /**
   * Builds the output boundary used to handle the results of a fetch todo request.
   *
   * @return The output boundary.
   */
  FetchToDoOutputBoundary fetch();

  /**
   * Builds the output boundary used to handle the results of todo updates.
   *
   * @return The output boundary.
   */
  UpdateToDoOutputBoundary update();
}

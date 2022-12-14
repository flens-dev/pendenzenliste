package pendenzenliste.ports.in;

/**
 * A factory that can be used to access ToDo specific input boundaries.
 */
public interface ToDoInputBoundaryFactory
{
  /**
   * Creates a new input boundary that can be used to fetch a ToDo.
   *
   * @return The boundary.
   */
  FetchToDoInputBoundary fetch();

  /**
   * Creates a new input boundary that can be used to delete a ToDo.
   *
   * @return The boundary.
   */
  DeleteToDoInputBoundary delete();
}

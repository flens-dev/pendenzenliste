package pendenzenliste.dropwizard;

import static java.util.Objects.requireNonNull;

import pendenzenliste.todos.boundary.in.FetchToDoRequest;
import pendenzenliste.todos.boundary.in.FetchTodoListRequest;
import pendenzenliste.todos.boundary.in.ToDoInputBoundaryFactory;

/**
 * A controller that can be used to invoke input boundaries.
 */
public class ToDoController
{
  private final ToDoInputBoundaryFactory todoFactory;

  /**
   * Creates a new instance.
   *
   * @param todoFactory The todo factory that should be used by this instance.
   */
  public ToDoController(final ToDoInputBoundaryFactory todoFactory)
  {
    this.todoFactory = requireNonNull(todoFactory, "The todo factory may not be null");
  }

  /**
   * Lists the todos.
   */
  public void list()
  {
    todoFactory.list().execute(new FetchTodoListRequest());
  }

  /**
   * Fetches a specific todo by its ID.
   *
   * @param id The ID of the todo.
   */
  public void fetch(final String id)
  {
    todoFactory.fetch().execute(new FetchToDoRequest(id));
  }
}

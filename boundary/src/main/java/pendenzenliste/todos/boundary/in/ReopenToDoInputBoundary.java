package pendenzenliste.todos.boundary.in;

import pendenzenliste.boundary.in.InputBoundary;
import pendenzenliste.todos.boundary.out.UpdateToDoOutputBoundary;
import pendenzenliste.todos.boundary.out.UpdateToDoResponse;

/**
 * An input boundary that can be used to reset a todo.
 */
public interface ReopenToDoInputBoundary
    extends InputBoundary<ResetToDoRequest, UpdateToDoResponse, UpdateToDoOutputBoundary>
{
}

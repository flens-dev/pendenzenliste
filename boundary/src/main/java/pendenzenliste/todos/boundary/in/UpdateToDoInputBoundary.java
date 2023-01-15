package pendenzenliste.todos.boundary.in;

import pendenzenliste.boundary.in.InputBoundary;
import pendenzenliste.todos.boundary.out.UpdateToDoOutputBoundary;
import pendenzenliste.todos.boundary.out.UpdateToDoResponse;

/**
 * An input boundary that can be used to update a todo.
 */
public interface UpdateToDoInputBoundary
    extends InputBoundary<UpdateToDoRequest, UpdateToDoResponse, UpdateToDoOutputBoundary>
{
}

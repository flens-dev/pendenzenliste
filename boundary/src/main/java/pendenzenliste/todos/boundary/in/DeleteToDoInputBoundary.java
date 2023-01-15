package pendenzenliste.todos.boundary.in;

import pendenzenliste.boundary.in.InputBoundary;
import pendenzenliste.todos.boundary.out.UpdateToDoOutputBoundary;
import pendenzenliste.todos.boundary.out.UpdateToDoResponse;

/**
 * An input boundary that cna be used to delete a ToDo.
 */
public interface DeleteToDoInputBoundary
    extends InputBoundary<DeleteToDoRequest, UpdateToDoResponse, UpdateToDoOutputBoundary>
{
}

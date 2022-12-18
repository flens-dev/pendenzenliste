package pendenzenliste.boundary.in;

import pendenzenliste.boundary.out.UpdateToDoOutputBoundary;
import pendenzenliste.boundary.out.UpdateToDoResponse;

/**
 * An input boundary that can be used to update a todo.
 */
public interface UpdateToDoInputBoundary
    extends InputBoundary<UpdateToDoRequest, UpdateToDoResponse, UpdateToDoOutputBoundary>
{
}

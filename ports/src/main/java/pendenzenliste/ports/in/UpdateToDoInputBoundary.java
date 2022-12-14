package pendenzenliste.ports.in;

import pendenzenliste.ports.out.UpdateToDoOutputBoundary;
import pendenzenliste.ports.out.UpdateToDoResponse;

/**
 * An input boundary that can be used to update a todo.
 */
public interface UpdateToDoInputBoundary
    extends InputBoundary<UpdateToDoRequest, UpdateToDoResponse, UpdateToDoOutputBoundary>
{
}

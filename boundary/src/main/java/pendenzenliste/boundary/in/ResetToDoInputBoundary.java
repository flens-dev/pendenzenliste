package pendenzenliste.boundary.in;

import pendenzenliste.boundary.out.UpdateToDoOutputBoundary;
import pendenzenliste.boundary.out.UpdateToDoResponse;

/**
 * An input boundary that can be used to reset a todo.
 */
public interface ResetToDoInputBoundary
    extends InputBoundary<ResetToDoRequest, UpdateToDoResponse, UpdateToDoOutputBoundary>
{
}

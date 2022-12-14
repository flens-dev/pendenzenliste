package pendenzenliste.ports.in;

import pendenzenliste.ports.out.UpdateToDoOutputBoundary;
import pendenzenliste.ports.out.UpdateToDoResponse;

/**
 * An input boundary that can be used to reset a todo.
 */
public interface ResetToDoInputBoundary
    extends InputBoundary<ResetToDoRequest, UpdateToDoResponse, UpdateToDoOutputBoundary>
{
}

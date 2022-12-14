package pendenzenliste.ports.in;

import pendenzenliste.ports.out.UpdateToDoOutputBoundary;
import pendenzenliste.ports.out.UpdateToDoResponse;

/**
 * An input boundary that can be used to complete a ToDo.
 */
public interface CompleteToDoInputBoundary
    extends InputBoundary<CompleteToDoRequest, UpdateToDoResponse, UpdateToDoOutputBoundary>
{
}

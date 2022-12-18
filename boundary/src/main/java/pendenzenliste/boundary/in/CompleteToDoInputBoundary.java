package pendenzenliste.boundary.in;

import pendenzenliste.boundary.out.UpdateToDoOutputBoundary;
import pendenzenliste.boundary.out.UpdateToDoResponse;

/**
 * An input boundary that can be used to complete a ToDo.
 */
public interface CompleteToDoInputBoundary
    extends InputBoundary<CompleteToDoRequest, UpdateToDoResponse, UpdateToDoOutputBoundary>
{
}

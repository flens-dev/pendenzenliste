package pendenzenliste.boundary.in;

import pendenzenliste.boundary.out.UpdateToDoOutputBoundary;
import pendenzenliste.boundary.out.UpdateToDoResponse;

/**
 * An input boundary that cna be used to delete a ToDo.
 */
public interface DeleteToDoInputBoundary
    extends InputBoundary<DeleteToDoRequest, UpdateToDoResponse, UpdateToDoOutputBoundary>
{
}

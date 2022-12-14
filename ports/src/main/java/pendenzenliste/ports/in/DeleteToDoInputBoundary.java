package pendenzenliste.ports.in;

import pendenzenliste.ports.out.UpdateToDoOutputBoundary;
import pendenzenliste.ports.out.UpdateToDoResponse;

/**
 * An input boundary that cna be used to delete a ToDo.
 */
public interface DeleteToDoInputBoundary
    extends InputBoundary<DeleteToDoRequest, UpdateToDoResponse, UpdateToDoOutputBoundary>
{
}

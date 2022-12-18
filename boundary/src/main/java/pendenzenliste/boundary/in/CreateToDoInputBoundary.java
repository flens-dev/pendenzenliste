package pendenzenliste.boundary.in;

import pendenzenliste.boundary.out.CreateToDoOutputBoundary;
import pendenzenliste.boundary.out.CreateToDoResponse;

/**
 * An input boundary that can be used to create a new ToDo.
 */
public interface CreateToDoInputBoundary
    extends InputBoundary<CreateToDoRequest, CreateToDoResponse, CreateToDoOutputBoundary>
{
}

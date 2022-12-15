package pendenzenliste.ports.in;

import pendenzenliste.ports.out.CreateToDoOutputBoundary;
import pendenzenliste.ports.out.CreateToDoResponse;

/**
 * An input boundary that can be used to create a new ToDo.
 */
public interface CreateToDoInputBoundary
    extends InputBoundary<CreateToDoRequest, CreateToDoResponse, CreateToDoOutputBoundary>
{
}

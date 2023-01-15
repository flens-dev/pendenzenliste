package pendenzenliste.todos.boundary.in;

import pendenzenliste.boundary.in.InputBoundary;
import pendenzenliste.todos.boundary.out.CreateToDoOutputBoundary;
import pendenzenliste.todos.boundary.out.CreateToDoResponse;

/**
 * An input boundary that can be used to create a new ToDo.
 */
public interface CreateToDoInputBoundary
    extends InputBoundary<CreateToDoRequest, CreateToDoResponse, CreateToDoOutputBoundary>
{
}

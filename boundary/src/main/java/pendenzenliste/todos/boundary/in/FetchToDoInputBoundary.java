package pendenzenliste.todos.boundary.in;

import pendenzenliste.boundary.in.InputBoundary;
import pendenzenliste.todos.boundary.out.FetchToDoOutputBoundary;
import pendenzenliste.todos.boundary.out.FetchToDoResponse;

/**
 * An input boundary that can be used to fetch a ToDo.
 */
public interface FetchToDoInputBoundary
    extends InputBoundary<FetchToDoRequest, FetchToDoResponse, FetchToDoOutputBoundary>
{
}

package pendenzenliste.boundary.in;

import pendenzenliste.boundary.out.FetchToDoOutputBoundary;
import pendenzenliste.boundary.out.FetchToDoResponse;

/**
 * An input boundary that can be used to fetch a ToDo.
 */
public interface FetchToDoInputBoundary
    extends InputBoundary<FetchToDoRequest, FetchToDoResponse, FetchToDoOutputBoundary>
{
}

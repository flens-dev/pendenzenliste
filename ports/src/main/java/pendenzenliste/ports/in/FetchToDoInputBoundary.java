package pendenzenliste.ports.in;

import pendenzenliste.ports.out.FetchToDoOutputBoundary;
import pendenzenliste.ports.out.FetchToDoResponse;

/**
 * An input boundary that can be used to fetch a ToDo.
 */
public interface FetchToDoInputBoundary
    extends InputBoundary<FetchToDoRequest, FetchToDoResponse, FetchToDoOutputBoundary>
{
}

package pendenzenliste.boundary.in;

import pendenzenliste.boundary.out.FetchToDoListOutputBoundary;
import pendenzenliste.boundary.out.FetchToDoListResponse;

/**
 * An input boundary that can be used to subscribe to a list of todos.
 */
public interface SubscribeToDoListInputBoundary extends
    InputBoundary<SubscribeToDoListRequest, FetchToDoListResponse, FetchToDoListOutputBoundary>
{
}

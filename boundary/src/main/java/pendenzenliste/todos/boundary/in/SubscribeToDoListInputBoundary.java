package pendenzenliste.todos.boundary.in;

import pendenzenliste.boundary.in.InputBoundary;
import pendenzenliste.todos.boundary.out.FetchToDoListOutputBoundary;
import pendenzenliste.todos.boundary.out.FetchToDoListResponse;

/**
 * An input boundary that can be used to subscribe to a list of todos.
 */
public interface SubscribeToDoListInputBoundary extends
    InputBoundary<SubscribeToDoListRequest, FetchToDoListResponse, FetchToDoListOutputBoundary>
{
}

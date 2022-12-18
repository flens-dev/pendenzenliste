package pendenzenliste.boundary.in;

import pendenzenliste.boundary.out.FetchToDoListOutputBoundary;
import pendenzenliste.boundary.out.FetchToDoListResponse;

/**
 * An input boundary that can be used to fetch a list of todos
 */
public interface FetchToDoListInputBoundary
    extends InputBoundary<FetchTodoListRequest, FetchToDoListResponse, FetchToDoListOutputBoundary>
{
}

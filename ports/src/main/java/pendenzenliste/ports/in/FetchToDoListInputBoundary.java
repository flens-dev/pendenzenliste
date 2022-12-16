package pendenzenliste.ports.in;

import pendenzenliste.ports.out.FetchToDoListOutputBoundary;
import pendenzenliste.ports.out.FetchToDoListResponse;

/**
 * An input boundary that can be used to fetch a list of todos
 */
public interface FetchToDoListInputBoundary
    extends InputBoundary<FetchTodoListRequest, FetchToDoListResponse, FetchToDoListOutputBoundary>
{
}

package pendenzenliste.todos.boundary.in;

import pendenzenliste.boundary.in.InputBoundary;
import pendenzenliste.todos.boundary.out.FetchToDoListOutputBoundary;
import pendenzenliste.todos.boundary.out.FetchToDoListResponse;

/**
 * An input boundary that can be used to fetch a list of todos
 */
public interface FetchToDoListInputBoundary
    extends InputBoundary<FetchTodoListRequest, FetchToDoListResponse, FetchToDoListOutputBoundary>
{
}

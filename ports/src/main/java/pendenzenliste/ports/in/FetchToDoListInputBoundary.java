package pendenzenliste.ports.in;

import pendenzenliste.ports.out.FetchToDoListResponse;
import pendenzenliste.ports.out.ListToDosOutputBoundary;

/**
 * An input boundary that can be used to fetch a list of todos
 */
public interface FetchToDoListInputBoundary
    extends InputBoundary<ListTodosRequest, FetchToDoListResponse, ListToDosOutputBoundary>
{
}

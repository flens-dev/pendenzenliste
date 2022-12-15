package pendenzenliste.ports.in;

import pendenzenliste.ports.out.ListToDosOutputBoundary;
import pendenzenliste.ports.out.ListToDosResponse;

/**
 * An input boundary that can be used to fetch a list of todos
 */
public interface ListTodosInputBoundary
    extends InputBoundary<ListTodosRequest, ListToDosResponse, ListToDosOutputBoundary>
{
}

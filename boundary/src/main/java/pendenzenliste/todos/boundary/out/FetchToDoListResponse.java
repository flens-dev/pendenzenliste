package pendenzenliste.todos.boundary.out;

import pendenzenliste.boundary.out.Response;

/**
 * A response that can be used to represent the various results to a list todos request.
 */
public interface FetchToDoListResponse
    extends Response<FetchToDoListOutputBoundary, FetchToDoListResponse>
{
}
